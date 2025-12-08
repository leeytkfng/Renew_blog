package board.backend.Auth.Application.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpCommand {
    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String address;
}
