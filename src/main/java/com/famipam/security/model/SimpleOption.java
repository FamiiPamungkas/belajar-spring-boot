package com.famipam.security.model;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SimpleOption {

    @EqualsAndHashCode.Include
    private String key;
    private String value;

}
