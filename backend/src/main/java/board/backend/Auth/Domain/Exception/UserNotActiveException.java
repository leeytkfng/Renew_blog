package board.backend.Auth.Domain.Exception;

public class UserNotActiveException extends RuntimeException{
    public UserNotActiveException(String message) {
        super(message);
    }
}
