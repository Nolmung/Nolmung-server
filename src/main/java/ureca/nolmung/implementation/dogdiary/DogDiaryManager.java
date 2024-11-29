package ureca.nolmung.implementation.dogdiary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.dogdiary.DogDiary;
import ureca.nolmung.persistence.dogdiary.DogDiaryRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DogDiaryManager {

    private final DogDiaryRepository dogDiaryRepository;
    public List<DogDiary> getDogList(Long diaryId) {
        return dogDiaryRepository.findByDiaryId(diaryId);
    }
}
