package ru.cft.template.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cft.template.constants.enums.BillStatus;
import ru.cft.template.entity.Bill;
import ru.cft.template.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID>, QuerydslPredicateExecutor<Bill> {
    Optional<Bill> findBySenderAndId(User sender, UUID id);

    Optional<Bill> findByRecipientAndId(User recipient, UUID id);

    Optional<Bill> findTopByRecipientAndBillStatusOrderByCreatedAtAsc(User recipient, BillStatus billStatus);

    @Query("""
            select coalesce(sum(bill.cost), 0)
            from Bill bill
            where bill.recipient.id = :recipientId and bill.billStatus='UNPAID'
            """)
    Long getTotalDebt(@Param("recipientId") UUID recipientId);
}