package com.famipam.security.repository;

import com.famipam.security.entity.Role;
import com.famipam.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
