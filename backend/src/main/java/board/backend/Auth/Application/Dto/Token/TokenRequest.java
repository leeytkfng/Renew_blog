package board.backend.Auth.Application.Dto.Token;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequest {
    private  String refreshToken;
}
