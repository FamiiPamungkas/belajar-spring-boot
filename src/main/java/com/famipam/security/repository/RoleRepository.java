package com.famipam.security.repository;

import com.famipam.security.entity.Menu;
import com.famipam.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT DISTINCT r FROM Role r JOIN r.menus m WHERE m = :menu")
    List<Role> findByMenu(Menu menu);

    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE r.authority = :authority AND r.id <> :excludeId")
    boolean existsByAuthority(String authority, long excludeId);
}
