package com.famipam.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class View extends BaseEntity {

    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String link;
    private boolean isMenu;

    @ManyToOne
    private View parent;

}
