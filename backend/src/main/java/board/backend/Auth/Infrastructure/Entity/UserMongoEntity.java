package board.backend.Auth.Infrastructure.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * 수정 #1 -> Mongo Db <-> JPA 는 복잡성이 너무 올라가서 Document로 바꿔야한다.</->
 */
@Document("users") //entity -> document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMongoEntity {

    @Id
    private String id;

    @Indexed(unique = true)  // unique index
    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("name")
    private String name;

    @Field("phone")
    private String phone;

    @Field("status")
    private String status;

    @Field("address")
    private String address;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("deleted_at")
    private LocalDateTime deletedAt;

    @Field("ip_address")
    private String ipAddress;

    @Field("last_login_tt")
    private LocalDateTime lastLoginAt;

    @Field("platform")
    private String platform;


    //생성자
    public UserMongoEntity(String id, String email, String password, String name,
                         String phone, String address, String status,
                         LocalDateTime createdAt, LocalDateTime lastLoginAt, String ipAddress,
                         LocalDateTime deletedAt, String platform) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.ipAddress = ipAddress;
        this.lastLoginAt = lastLoginAt;
        this.platform = platform;
    }

    //업데이터 메서드들
    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateStatus(String status) {
        this.status= status;
    }

    public void updateLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
