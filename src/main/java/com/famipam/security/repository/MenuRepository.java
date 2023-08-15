package com.famipam.security.repository;

import com.famipam.security.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT m FROM Menu m WHERE m.group LIKE %:group% group by m.group")
    List<Menu> searchGroupMenu(@Param("group") String groupName);
}
