package com.famipam.security.repository;

import com.famipam.security.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(
            "SELECT DISTINCT m FROM Menu m LEFT JOIN m.parent p " +
                    " WHERE (m.parent = null or p.parent = null) " +
                    " and m.visible = true " +
                    " and (m.link = '' or m.link = null) " +
                    " order by m.seq "
    )
    List<Menu> findAllForParent();
}
