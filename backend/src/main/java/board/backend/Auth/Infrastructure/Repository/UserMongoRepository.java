package board.backend.Auth.Infrastructure.Repository;

import board.backend.Auth.Infrastructure.Entity.UserMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserMongoEntity, String> {
    Optional<UserMongoEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
