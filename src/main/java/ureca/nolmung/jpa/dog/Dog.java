package ureca.nolmung.jpa.dog;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.dog.Enum.DogSize;
import ureca.nolmung.jpa.dog.Enum.Gender;
import ureca.nolmung.jpa.user.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 30, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private boolean neuteredYn;

    @Column(length = 30, nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DogSize size;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    public void update(DogReq req) {
        this.name = req.dogName();
        this.gender = req.gender();
        this.neuteredYn = req.neuterYn();
        this.type = req.dogType();
        this.size = req.size();
        this.birth = req.birth();
        this.profileImageUrl = req.profileUrl();
    }


}
