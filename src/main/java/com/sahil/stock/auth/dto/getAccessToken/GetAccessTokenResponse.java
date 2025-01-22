package com.sahil.stock.auth.dto.getAccessToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessTokenResponse {
    private String accessToken;
}
