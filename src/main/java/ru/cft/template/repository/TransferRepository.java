package ru.cft.template.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.cft.template.models.Transfer;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID>, QuerydslPredicateExecutor<Transfer> {
}
