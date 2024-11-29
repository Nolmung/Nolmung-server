package ureca.nolmung.jpa.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.business.user.dto.request.SignUpReq;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.user.Enum.Gender;
import ureca.nolmung.jpa.user.Enum.Provider;
import ureca.nolmung.jpa.user.Enum.UserRole;
import ureca.nolmung.jpa.user.Enum.UserStatus;

import java.util.List;

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

    //@Column(length = 30, nullable = false)
    @Column(length = 30)
    private String nickname;

    //@Column(length = 30, nullable = false)
    @Column(length = 30)
    private String addressProvince;    // 거주지 시도명

    //@Column(length = 30, nullable = false)
    @Column(length = 30)
    private String address_District;    // 거주지 시군구명

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    //@Column(nullable = false)
    private Long age;

    @Enumerated(EnumType.STRING)
    //@Column(nullable = false)
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Diary> diaries;

    public User(String name, String profileImageUrl, String email)
    {
        // 소셜로그인을 통해 받아올 수 있는 정보들 설정
        this.name=name;
        this.profileImageUrl=profileImageUrl;
        this.email=email;
        this.provider = Provider.KAKAO;
        this.role=UserRole.GUEST;
        this.status = UserStatus.ACTIVE;
    }

    public void setSignUpReq(SignUpReq req)
    {
        // 화면에서 사용자를 통해 입력 받는 정보들 설정
        this.nickname=req.getNickname();
        this.addressProvince=req.getAddressProvince();
        this.address_District =req.getAddressDistrict();
        this.age = req.getAge();
        this.gender = req.getGender();
        this.role=UserRole.USER;
        System.out.println(this.nickname);
    }
}
