package com.famipam.security.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

}
