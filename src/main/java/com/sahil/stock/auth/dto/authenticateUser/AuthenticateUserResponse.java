package com.sahil.stock.auth.dto.authenticateUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateUserResponse {
    private String accessToken;
    private String refreshToken;
}
