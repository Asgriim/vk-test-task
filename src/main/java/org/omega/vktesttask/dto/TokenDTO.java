package org.omega.vktesttask.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@RequiredArgsConstructor
public class TokenDTO {
    private final String token;
    private final String tokenType = "Bearer";
    private final long liveTime;
}
