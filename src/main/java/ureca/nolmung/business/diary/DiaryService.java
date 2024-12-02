package ureca.nolmung.business.diary;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.request.UpdateDiaryReq;
import ureca.nolmung.business.diary.dto.response.*;
import ureca.nolmung.implementation.diary.DiaryManager;
import ureca.nolmung.implementation.diary.dtomapper.DiaryDtoMapper;
import ureca.nolmung.implementation.diaryplace.DiaryPlaceManager;
import ureca.nolmung.implementation.dogdiary.DogDiaryManager;
import ureca.nolmung.implementation.media.MediaManager;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.diaryplace.DiaryPlace;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.dogdiary.DogDiary;
import ureca.nolmung.jpa.media.Media;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService implements DiaryUseCase {
    private final DiaryManager diaryManager;
    private final UserManager userManager;
    private final DiaryDtoMapper diaryDtoMapper;
    private final DogDiaryManager dogDiaryManager;
    private final DiaryPlaceManager diaryPlaceManager;

    @Override
    @Transactional
    public AddDiaryResp addDiary(Long userId, AddDiaryReq req) {
        User user = userManager.validateUserExistence(userId);

        return diaryManager.addDiary(user, req);
    }

    @Override
    public DiaryListResp getAllDiaries(Long userId) {
        User user = userManager.validateUserExistence(userId);
        List<Diary> diaryList = diaryManager.getDiaryList(userId);

        return diaryDtoMapper.toAddDiaryResp(user, diaryList);
    }


    @Override
    @Transactional(readOnly = true)
    public DiaryDetailResp getDetailDiary(Long diaryId) {
        Diary diaryCheck = diaryManager.checkExistDiary(diaryId);
        Diary diary = diaryManager.getDetailDiary(diaryCheck.getId());
        List<DogDiary> dogList = dogDiaryManager.getDogList(diaryCheck.getId());
        List<Place> placeList = diaryPlaceManager.getPlaceList(diaryCheck.getId());
        List<Media> mediaList = diary.getMediaList() != null ? diary.getMediaList() : List.of();

        return diaryDtoMapper.toDiaryDetailResp(diary, dogList, placeList, mediaList);
    }

    @Override
    @Transactional
    public DeleteDiaryResp deleteDiary(Long diaryId) {
        Diary diaryCheck = diaryManager.checkExistDiary(diaryId);
        return diaryManager.deleteDiary(diaryCheck);
    }

    @Override
    @Transactional
    public UpdateDiaryResp updateDiary(Long diaryId, UpdateDiaryReq req) {
        diaryManager.checkDiaryWriter(req.userId(), diaryId);
        Diary diary = diaryManager.checkExistDiary(diaryId);

        return diaryManager.updateDiary(diary, req);
    }
}
