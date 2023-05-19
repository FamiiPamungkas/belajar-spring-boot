package com.famipam.security.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class View extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sequence")
    private Long id;

    private String name;
    private String description;
    private String link;
    private boolean isMenu;

    @ManyToOne
    private View parent;

}
