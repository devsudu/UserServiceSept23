package dev.sudu.userservicesept23.repositories;

import dev.sudu.userservicesept23.models.Token;
import dev.sudu.userservicesept23.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save(Token token);

    List<Token> findAllByUserAndIsActiveAndExpiryAtAfter(User user, Boolean isActive, Long currentTime);

    Optional<Token> findByToken(String token);
}
