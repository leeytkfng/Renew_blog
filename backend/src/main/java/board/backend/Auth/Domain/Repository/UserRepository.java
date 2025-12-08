package board.backend.Auth.Domain.Repository;

import board.backend.Auth.Domain.Model.Entity.User;
import board.backend.Auth.Domain.Model.Value.Email;
import board.backend.Auth.Domain.Model.Value.UserId;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository {
    //Command용 메서드 부분
    User save(User user);
    void delete(UserId userId);

    //Query용 메서드 부분
    Optional<User> findById(UserId userId);
    Optional<User> findByEmail(Email email);
    boolean existsByEmail(Email email);
}
