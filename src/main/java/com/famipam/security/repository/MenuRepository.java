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

    @Query("SELECT m.group FROM Menu m group by m.group")
    List<String> findGroupList();

    @Query("SELECT COUNT(u) > 0 FROM Menu u WHERE u.authority = :authority AND u.id <> :excludeId")
    boolean existsByAuthority(String authority, long excludeId);

    @Query("SELECT m.seq FROM Menu m WHERE m.parent = :parent order by m.seq desc LIMIT 1")
    int getParentLastSequence(Menu parent);
}
