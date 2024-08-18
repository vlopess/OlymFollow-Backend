package com.email.EmailOlympicsFollow.repositories;

import com.email.EmailOlympicsFollow.entitites.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
