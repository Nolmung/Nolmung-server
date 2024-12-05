package ureca.nolmung.jpa.user;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.business.user.dto.request.UserReq;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.diary.Diary;
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

    @Column(length = 30)
    private String nickname;

    @Column(length = 30)
    private String addressProvince; // 거주지

    @Column(name = "mapx")
    private double userLatitude;

    @Column(name = "mapy")
    private double userLongitude;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    private Long age;

    @Enumerated(EnumType.STRING)
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

    @Column(nullable = false)
    private Long diaryCount = 0L;


    public void singUp(UserReq req)
    {
        this.nickname=req.userNickname();
        this.addressProvince=req.userAddressProvince();
        this.age = req.userAge();
        this.gender = req.userGender();
        this.role=UserRole.USER;
    }

    public void updateUserInfo(UserReq req) {
        this.nickname = req.userNickname();
        this.addressProvince = req.userAddressProvince();
        this.age = req.userAge();
        this.gender = req.userGender();
    }

    public void setSocialLoginInfo(String name, String profileImageUrl, String email, Provider provider)
    {
        this.name=name;
        this.profileImageUrl=profileImageUrl;
        this.email=email;
        this.provider = provider;
        this.role=UserRole.GUEST;
        this.status = UserStatus.ACTIVE;
    }

    public void incrementDiaryCount()
    {
        this.diaryCount++;
    }

    public void decrementDiaryCount()
    {
        if(this.diaryCount > 0)
        {
            this.diaryCount--;
        }
    }
}
