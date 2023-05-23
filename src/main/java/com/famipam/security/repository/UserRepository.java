package com.famipam.security.repository;

import com.famipam.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username AND u.id <> :userId")
    boolean existsByUsernameExcludingUserId(String username, long userId);
}
