package ureca.nolmung.jpa.dogDiary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.user.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DogDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;
}
