package board.backend.Auth.Application.Dto.Login;

import board.backend.Auth.Application.Dto.Token.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private TokenResponse token;
    private String userId;
    private String email;
    private String name;
}
