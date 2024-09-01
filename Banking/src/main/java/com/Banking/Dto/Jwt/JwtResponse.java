package com.Banking.Dto.Jwt;

import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private String username;
}
