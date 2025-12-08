package board.backend.Auth.Domain.Exception;

public class DuplicateEmailException  extends RuntimeException{
    private DuplicateEmailException(String message) {
        super(message);
    }
}
