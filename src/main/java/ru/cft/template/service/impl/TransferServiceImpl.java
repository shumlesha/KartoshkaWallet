package ru.cft.template.service.impl;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.constants.enums.OperationDirection;
import ru.cft.template.constants.enums.TransferStatus;
import ru.cft.template.dto.transfer.CreateTransferModel;
import ru.cft.template.dto.transfer.TransferFilter;
import ru.cft.template.exception.transfer.NotUserTransferException;
import ru.cft.template.exception.transfer.SameUserTransferException;
import ru.cft.template.exception.transfer.TransferNotFoundException;
import ru.cft.template.exception.user.UserNotFoundByPhoneException;
import ru.cft.template.exception.wallet.NotEnoughMoneyException;
import ru.cft.template.exception.wallet.WalletNotFoundException;
import ru.cft.template.models.QTransfer;
import ru.cft.template.models.Transfer;
import ru.cft.template.models.User;
import ru.cft.template.models.Wallet;
import ru.cft.template.repository.TransferRepository;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.repository.WalletRepository;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.TransferService;
import ru.cft.template.util.QPredicates;

import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    TransferRepository transferRepository;
    WalletRepository walletRepository;
    UserRepository userRepository;

    @Override
    @Transactional
    public Transfer sendTransfer(SessionUser sessionUser, CreateTransferModel createTransferModel) {
        User fromUser = sessionUser.getSession().getUser();
        Wallet fromUserWallet = fromUser.getWallet();
        Long cost = createTransferModel.getCost();

        if (fromUserWallet.getId().equals(createTransferModel.getWalletId()) ||
            fromUser.getPhoneNumber().equals(createTransferModel.getPhoneNumber())) {
            throw new SameUserTransferException();
        }

        if (fromUserWallet.getBalance() < cost) {
            throw new NotEnoughMoneyException(cost-fromUserWallet.getBalance());
        }

        Object paymentInfo = createTransferModel.getWalletId() != null ? createTransferModel.getWalletId() : createTransferModel.getPhoneNumber();

        return switch (paymentInfo) {
            case UUID walletId -> sendTransferByWalletId(sessionUser, createTransferModel);
            case String phoneNumber -> sendTransferByPhoneNumber(sessionUser, createTransferModel);
            default -> throw new IllegalArgumentException();
        };
    }


    private Transfer sendTransferByWalletId(SessionUser sessionUser, CreateTransferModel createTransferModel) {
        UUID walletId = createTransferModel.getWalletId();
        Long cost = createTransferModel.getCost();

        Wallet toUserWallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        User fromUser = sessionUser.getSession().getUser();
        Wallet fromUserWallet = fromUser.getWallet();


        Transfer transfer = new Transfer();
        transfer.setCost(cost);
        transfer.setSender(fromUser);
        transfer.setRecipient(toUserWallet.getOwner());
        transfer.setComment(createTransferModel.getComment());
        transfer.setTransferStatus(TransferStatus.PAID);


        fromUserWallet.setBalance(fromUserWallet.getBalance()-cost);
        toUserWallet.setBalance(toUserWallet.getBalance()+cost);

        walletRepository.save(fromUserWallet);
        walletRepository.save(toUserWallet);

        return transferRepository.save(transfer);
    }

    private Transfer sendTransferByPhoneNumber(SessionUser sessionUser, CreateTransferModel createTransferModel) {

        String phoneNumber = createTransferModel.getPhoneNumber();

        User fromUser = sessionUser.getSession().getUser();
        Wallet fromUserWallet = fromUser.getWallet();

        User toUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundByPhoneException(phoneNumber));
        Wallet toUserWallet = toUser.getWallet();


        Long cost = createTransferModel.getCost();

        Transfer transfer = new Transfer();
        transfer.setCost(cost);
        transfer.setSender(fromUser);
        transfer.setRecipient(toUser);
        transfer.setComment(createTransferModel.getComment());
        transfer.setTransferStatus(TransferStatus.PAID);


        fromUserWallet.setBalance(fromUserWallet.getBalance()-cost);
        toUserWallet.setBalance(toUserWallet.getBalance()+cost);

        walletRepository.save(fromUserWallet);
        walletRepository.save(toUserWallet);

        return transferRepository.save(transfer);

    }

    @Override
    public Transfer getTransfer(SessionUser sessionUser, UUID id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new TransferNotFoundException(id));

        User user = sessionUser.getSession().getUser();

        if (!(transfer.getRecipient().equals(user) || transfer.getSender().equals(user))) {
            throw new NotUserTransferException(id);
        }

        return transfer;
    }

    @Override
    public Page<Transfer> getTransfers(SessionUser sessionUser, TransferFilter transferFilter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(transferFilter.direction(), direction -> getPredicateForDirection(sessionUser, direction))
                .add(transferFilter.transferStatus(), QTransfer.transfer.transferStatus::eq)
                .add(transferFilter.recipientId(), QTransfer.transfer.recipient.id::eq)
                .build();


        return transferRepository.findAll(predicate, pageable);
    }

    private Predicate getPredicateForDirection(SessionUser sessionUser, OperationDirection direction) {

        BooleanExpression withoutDirection = QTransfer.transfer.sender.id.eq(sessionUser.getId())
                .or(QTransfer.transfer.recipient.id.eq(sessionUser.getId()));

        if (direction == null) {
            return withoutDirection;
        }

        return switch (direction) {
            case INCOMING -> QTransfer.transfer.recipient.id.eq(sessionUser.getId());
            case OUTGOING -> QTransfer.transfer.sender.id.eq(sessionUser.getId());
            default -> withoutDirection;
        };
    }
}
