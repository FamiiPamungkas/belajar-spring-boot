package com.famipam.security.repository;

import com.famipam.security.entity.Role;
import com.famipam.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findUserByUsernameAndIdNot(String username, long id);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username AND u.id <> :excludeId")
    boolean existsByUsername(String username, long excludeId);

    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r where r =:role")
    List<User> findByRole(Role role);

    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r " +
            " WHERE (" +
            " u.firstname LIKE %:search% OR " +
            " u.lastname LIKE %:search% OR " +
            " u.email LIKE %:search% OR " +
            " u.username LIKE %:search%" +
            " ) " +
            " AND (:roleId IS NULL OR r.id = :roleId) " +
            " AND (:active IS NULL OR u.active = :active) " +
            " ORDER BY u.firstname ")
    Page<User> findByFilter(String search, Long roleId, Boolean active, Pageable pageable);
}
