package ru.cft.template.service.impl;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.constants.enums.BillStatus;
import ru.cft.template.dto.bill.BillFilter;
import ru.cft.template.dto.bill.CreateBillModel;
import ru.cft.template.exception.bill.*;
import ru.cft.template.exception.user.UserNotFoundException;
import ru.cft.template.exception.wallet.NotEnoughMoneyException;
import ru.cft.template.models.Bill;
import ru.cft.template.models.QBill;
import ru.cft.template.models.User;
import ru.cft.template.models.Wallet;
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

    @Override
    @Transactional
    public Bill createBill(SessionUser user, CreateBillModel createBillModel) {

        User recipient = userRepository.findById(createBillModel.getRecipientId())
                .orElseThrow(() -> new UserNotFoundException(createBillModel.getRecipientId()));

        if (recipient.getId().equals(user.getId())) {
            throw new SameUserBillException();
        }

        Bill bill = new Bill();
        bill.setCost(createBillModel.getCost());
        bill.setSender(user.getSession().getUser());
        bill.setRecipient(recipient);
        bill.setComment(createBillModel.getComment());
        bill.setBillStatus(BillStatus.UNPAID);

        return billRepository.save(bill);
    }

    @Override
    @Transactional
    public Bill cancelBill(SessionUser sessionUser, UUID id) {
        User sender = sessionUser.getSession().getUser();

        Bill bill = billRepository.findBySenderAndId(sender, id)
                .orElseThrow(() -> new NotBillOwnerException(id));

        if (!bill.getBillStatus().equals(BillStatus.UNPAID)) {
            throw new BillNotAllowedToCancelException(id);
        }

        bill.setBillStatus(BillStatus.CANCELLED);

        return billRepository.save(bill);
    }

    @Override
    @Transactional
    public Bill payBill(SessionUser sessionUser, UUID id) {
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

        return billRepository.save(bill);
    }



    @Override
    public Bill getBill(SessionUser sessionUser, UUID id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException(id));

        User user = sessionUser.getSession().getUser();

        if (!(bill.getRecipient().equals(user) || bill.getSender().equals(user))) {
            throw new NotUserBillException(id);
        }

        return bill;
    }

    @Override
    public Page<Bill> getBills(SessionUser sessionUser, BillFilter billFilter, Pageable pageable) {

        Predicate predicate = QPredicates.builder()
                .add(billFilter.billStatus(), QBill.bill.billStatus::eq)
                .add(billFilter.startDate(), QBill.bill.createdAt::after)
                .add(billFilter.endDate(), QBill.bill.createdAt::before)
                .add(billFilter.billId(), QBill.bill.id::eq)
                .build();


        return billRepository.findAll(predicate, pageable);
    }

    @Override
    public Bill getOldestUnpaidBill(SessionUser sessionUser) {
        return billRepository.findTopByRecipientAndBillStatusOrderByCreatedAtAsc(sessionUser.getSession().getUser(),
                BillStatus.UNPAID).orElseThrow(NoUnpaidBillsException::new);
    }

    @Override
    public Long getTotalDebt(SessionUser sessionUser) {
        return billRepository.getTotalDebt(sessionUser.getId());
    }
}
