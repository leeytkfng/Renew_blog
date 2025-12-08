package board.backend.Auth.Application.Query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserByEmailQuery {
    private final String email;
}
