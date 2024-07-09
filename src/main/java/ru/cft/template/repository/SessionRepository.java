package ru.cft.template.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cft.template.entity.Session;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    Optional<Session> findByAccessTokenAndEnabledTrue(String token);


    Optional<Session> findByRefreshToken(String refreshToken);
}