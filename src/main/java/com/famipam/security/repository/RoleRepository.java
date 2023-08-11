package com.famipam.security.repository;

import com.famipam.security.entity.Menu;
import com.famipam.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT DISTINCT r FROM Role r JOIN r.menus m WHERE m = :menu")
    List<Role> findByMenu(Menu menu);
}
