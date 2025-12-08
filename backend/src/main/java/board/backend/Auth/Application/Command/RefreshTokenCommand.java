package board.backend.Auth.Application.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenCommand {
    private final String refreshToken;
}
