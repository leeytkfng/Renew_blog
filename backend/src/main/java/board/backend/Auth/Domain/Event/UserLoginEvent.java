package board.backend.Auth.Domain.Event;

import board.backend.Auth.Domain.Model.Vo.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginEvent {
    private final UserId userId;
    private final String email;
}
