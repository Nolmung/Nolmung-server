package ureca.nolmung.jpa.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.user.Enum.Gender;
import ureca.nolmung.jpa.user.Enum.Provider;
import ureca.nolmung.jpa.user.Enum.UserRole;
import ureca.nolmung.jpa.user.Enum.UserStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Column(length = 30, nullable = false)
    private String addressProvince;    // 거주지 시도명

    @Column(length = 30, nullable = false)
    private String address_District;    // 거주지 시군구명

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    @Column(nullable = false)
    private Long age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(length = 50, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;
}
