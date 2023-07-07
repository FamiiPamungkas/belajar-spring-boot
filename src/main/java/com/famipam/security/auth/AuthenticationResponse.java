package com.famipam.security.auth;

import com.famipam.security.dto.UserAuthDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private String refreshToken;
    private UserAuthDTO user;

}
