package com.betroc.repository;

import com.betroc.model.User;
import com.betroc.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
        VerificationToken findByToken(String token);

        VerificationToken findByUser(User user);
}
