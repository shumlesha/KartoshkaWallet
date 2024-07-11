package ru.cft.template.service.impl;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.constants.enums.OperationDirection;
import ru.cft.template.constants.enums.TransferStatus;
import ru.cft.template.dto.transfer.CreateTransferRequest;
import ru.cft.template.dto.transfer.TransferDto;
import ru.cft.template.dto.transfer.TransferFilter;
import ru.cft.template.entity.QTransfer;
import ru.cft.template.exception.transfer.NotUserTransferException;
import ru.cft.template.exception.transfer.SameUserTransferException;
import ru.cft.template.exception.transfer.TransferNotFoundException;
import ru.cft.template.exception.user.UserNotFoundByPhoneException;
import ru.cft.template.exception.wallet.NotEnoughMoneyException;
import ru.cft.template.exception.wallet.WalletNotFoundException;
import ru.cft.template.mapper.TransferMapper;
import ru.cft.template.entity.Transfer;
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;
import ru.cft.template.repository.TransferRepository;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.repository.WalletRepository;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.TransferService;
import ru.cft.template.util.QPredicates;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransferMapper transferMapper;

    @Override
    @Transactional
    public TransferDto sendTransfer(SessionUser sessionUser, CreateTransferRequest createTransferRequest) {
        User fromUser = sessionUser.getSession().getUser();
        Wallet fromUserWallet = fromUser.getWallet();
        Long cost = createTransferRequest.getCost();

        if (fromUserWallet.getId().equals(createTransferRequest.getWalletId()) ||
            fromUser.getPhoneNumber().equals(createTransferRequest.getPhoneNumber())) {
            throw new SameUserTransferException();
        }

        if (fromUserWallet.getBalance() < cost) {
            throw new NotEnoughMoneyException(cost-fromUserWallet.getBalance());
        }

        Object paymentInfo = createTransferRequest.getWalletId() != null ? createTransferRequest.getWalletId() : createTransferRequest.getPhoneNumber();

        return transferMapper.toDTO(transferRepository.saveAndFlush(switch (paymentInfo) {
            case UUID walletId -> sendTransferByWalletId(sessionUser, createTransferRequest);
            case String phoneNumber -> sendTransferByPhoneNumber(sessionUser, createTransferRequest);
            default -> throw new IllegalArgumentException();
        }));
    }


    private Transfer sendTransferByWalletId(SessionUser sessionUser, CreateTransferRequest createTransferRequest) {
        UUID walletId = createTransferRequest.getWalletId();
        Long cost = createTransferRequest.getCost();

        Wallet toUserWallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        User fromUser = sessionUser.getSession().getUser();
        Wallet fromUserWallet = fromUser.getWallet();


        Transfer transfer = new Transfer();
        transfer.setCost(cost);
        transfer.setSender(fromUser);
        transfer.setRecipient(toUserWallet.getOwner());
        transfer.setComment(createTransferRequest.getComment());
        transfer.setTransferStatus(TransferStatus.PAID);


        fromUserWallet.setBalance(fromUserWallet.getBalance()-cost);
        toUserWallet.setBalance(toUserWallet.getBalance()+cost);

        walletRepository.save(fromUserWallet);
        walletRepository.save(toUserWallet);

        return transfer;
    }

    private Transfer sendTransferByPhoneNumber(SessionUser sessionUser, CreateTransferRequest createTransferRequest) {

        String phoneNumber = createTransferRequest.getPhoneNumber();

        User fromUser = sessionUser.getSession().getUser();
        Wallet fromUserWallet = fromUser.getWallet();

        User toUser = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundByPhoneException(phoneNumber));
        Wallet toUserWallet = toUser.getWallet();


        Long cost = createTransferRequest.getCost();

        Transfer transfer = new Transfer();
        transfer.setCost(cost);
        transfer.setSender(fromUser);
        transfer.setRecipient(toUser);
        transfer.setComment(createTransferRequest.getComment());
        transfer.setTransferStatus(TransferStatus.PAID);


        fromUserWallet.setBalance(fromUserWallet.getBalance()-cost);
        toUserWallet.setBalance(toUserWallet.getBalance()+cost);

        walletRepository.save(fromUserWallet);
        walletRepository.save(toUserWallet);

        return transfer;

    }

    @Override
    public TransferDto getTransfer(SessionUser sessionUser, UUID id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new TransferNotFoundException(id));

        User user = sessionUser.getSession().getUser();

        if (!(transfer.getRecipient().equals(user) || transfer.getSender().equals(user))) {
            throw new NotUserTransferException(id);
        }

        return transferMapper.toDTO(transfer);
    }

    @Override
    public Page<TransferDto> getTransfers(SessionUser sessionUser, TransferFilter transferFilter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(transferFilter.direction(), direction -> getPredicateForDirection(sessionUser, direction))
                .add(transferFilter.transferStatus(), QTransfer.transfer.transferStatus::eq)
                .add(transferFilter.recipientId(), QTransfer.transfer.recipient.id::eq)
                .build();


        return transferRepository.findAll(predicate, pageable).map(transferMapper::toDTO);
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
