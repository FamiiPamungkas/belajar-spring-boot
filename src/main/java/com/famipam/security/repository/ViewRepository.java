package com.famipam.security.repository;

import com.famipam.security.entity.Role;
import com.famipam.security.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewRepository extends JpaRepository<View, Long> {

}
