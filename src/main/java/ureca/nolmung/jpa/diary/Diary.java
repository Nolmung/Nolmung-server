package ureca.nolmung.jpa.diary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.diaryplace.DiaryPlace;
import ureca.nolmung.jpa.dogdiary.DogDiary;
import ureca.nolmung.jpa.media.Media;
import ureca.nolmung.jpa.user.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean publicYn;

    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Media> mediaList;

    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DiaryPlace> diaryPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DogDiary> dogDiaries = new ArrayList<>();

    public void updateDiary(String title, String content, boolean publicYn) {
        this.title = title;
        this.content = content;
        this.publicYn = publicYn;
    }

    public void addDogDiary(DogDiary dogDiary) {
        this.dogDiaries.add(dogDiary);
    }
}

