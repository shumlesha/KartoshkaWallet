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
import ru.cft.template.constants.enums.BillStatus;
import ru.cft.template.constants.enums.OperationDirection;
import ru.cft.template.dto.bill.BillDto;
import ru.cft.template.dto.bill.BillFilter;
import ru.cft.template.dto.bill.CreateBillRequest;
import ru.cft.template.dto.bill.DebtDto;
import ru.cft.template.exception.bill.*;
import ru.cft.template.exception.user.UserNotFoundException;
import ru.cft.template.exception.wallet.NotEnoughMoneyException;
import ru.cft.template.mapper.BillMapper;
import ru.cft.template.models.*;
import ru.cft.template.repository.BillRepository;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.repository.WalletRepository;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.BillService;
import ru.cft.template.util.QPredicates;

import java.util.UUID;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    BillRepository billRepository;
    UserRepository userRepository;
    WalletRepository walletRepository;
    BillMapper billMapper;

    @Override
    @Transactional
    public BillDto createBill(SessionUser user, CreateBillRequest createBillRequest) {

        User recipient = userRepository.findById(createBillRequest.getRecipientId())
                .orElseThrow(() -> new UserNotFoundException(createBillRequest.getRecipientId()));

        if (recipient.getId().equals(user.getId())) {
            throw new SameUserBillException();
        }

        Bill bill = new Bill(
                createBillRequest.getCost(),
                user.getSession().getUser(),
                recipient,
                createBillRequest.getComment(),
                BillStatus.UNPAID
        );

        return billMapper.toDTO(billRepository.save(bill));
    }

    @Override
    @Transactional
    public BillDto cancelBill(SessionUser sessionUser, UUID id) {
        User sender = sessionUser.getSession().getUser();

        Bill bill = billRepository.findBySenderAndId(sender, id)
                .orElseThrow(() -> new NotBillOwnerException(id));

        if (!bill.getBillStatus().equals(BillStatus.UNPAID)) {
            throw new BillNotAllowedToCancelException(id);
        }

        bill.setBillStatus(BillStatus.CANCELLED);

        return billMapper.toDTO(billRepository.save(bill));
    }

    @Override
    @Transactional
    public BillDto payBill(SessionUser sessionUser, UUID id) {
        User recipient = sessionUser.getSession().getUser();

        Bill bill = billRepository.findByRecipientAndId(recipient, id)
                .orElseThrow(() -> new NotBillRecipientException(id));

        if (bill.getBillStatus().equals(BillStatus.PAID)) {
            throw new BillAlreadyPaidException(id);
        }

        if (bill.getBillStatus().equals(BillStatus.CANCELLED)) {
            throw new CancelledBillException(id);
        }

        Wallet recipientWallet = recipient.getWallet();
        Long recipientBalance = recipientWallet.getBalance();

        Wallet senderWallet = bill.getSender().getWallet();
        Long senderBalance = senderWallet.getBalance();

        if (recipientBalance < bill.getCost()) {
            throw new NotEnoughMoneyException(bill.getCost()-recipientBalance);
        }


        recipientWallet.setBalance(recipientBalance-bill.getCost());
        senderWallet.setBalance(senderBalance+bill.getCost());

        bill.setBillStatus(BillStatus.PAID);

        walletRepository.save(recipientWallet);
        walletRepository.save(senderWallet);

        return billMapper.toDTO(billRepository.save(bill));
    }



    @Override
    public BillDto getBill(SessionUser sessionUser, UUID id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException(id));

        User user = sessionUser.getSession().getUser();

        if (!(bill.getRecipient().equals(user) || bill.getSender().equals(user))) {
            throw new NotUserBillException(id);
        }

        return billMapper.toDTO(bill);
    }

    @Override
    public Page<BillDto> getBills(SessionUser sessionUser, BillFilter billFilter, Pageable pageable) {

        Predicate predicate = QPredicates.builder()
                .add(billFilter.direction(), direction -> getPredicateForDirection(sessionUser, direction))
                .add(billFilter.billStatus(), QBill.bill.billStatus::eq)
                .add(billFilter.startDate(), QBill.bill.createdAt::after)
                .add(billFilter.endDate(), QBill.bill.createdAt::before)
                .add(billFilter.billId(), QBill.bill.id::eq)
                .build();


        return billRepository.findAll(predicate, pageable).map(billMapper::toDTO);
    }

    @Override
    public BillDto getOldestUnpaidBill(SessionUser sessionUser) {
        return billMapper.toDTO(billRepository.findTopByRecipientAndBillStatusOrderByCreatedAtAsc(sessionUser.getSession().getUser(),
                BillStatus.UNPAID).orElseThrow(NoUnpaidBillsException::new));
    }

    @Override
    public DebtDto getTotalDebt(SessionUser sessionUser) {
        return new DebtDto(billRepository.getTotalDebt(sessionUser.getId()));
    }

    private Predicate getPredicateForDirection(SessionUser sessionUser, OperationDirection direction) {

        BooleanExpression withoutDirection = QBill.bill.sender.id.eq(sessionUser.getId())
                .or(QBill.bill.recipient.id.eq(sessionUser.getId()));

        if (direction == null) {
            return withoutDirection;
        }

        return switch (direction) {
            case INCOMING -> QBill.bill.recipient.id.eq(sessionUser.getId());
            case OUTGOING -> QBill.bill.sender.id.eq(sessionUser.getId());
            default -> withoutDirection;
        };
    }
}
