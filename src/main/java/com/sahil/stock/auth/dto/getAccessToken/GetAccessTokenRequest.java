package com.sahil.stock.auth.dto.getAccessToken;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessTokenRequest {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
