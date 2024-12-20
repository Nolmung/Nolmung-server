package ureca.nolmung.business.diary;

import static java.time.LocalDate.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.request.UpdateDiaryReq;
import ureca.nolmung.business.diary.dto.response.AddDiaryResp;
import ureca.nolmung.business.diary.dto.response.DeleteDiaryResp;
import ureca.nolmung.business.diary.dto.response.DiaryDetailResp;
import ureca.nolmung.business.diary.dto.response.DiaryListResp;
import ureca.nolmung.business.diary.dto.response.UpdateDiaryResp;
import ureca.nolmung.implementation.badge.BadgeManager;
import ureca.nolmung.implementation.diary.DiaryManager;
import ureca.nolmung.implementation.diary.dtomapper.DiaryDtoMapper;
import ureca.nolmung.implementation.diaryplace.DiaryPlaceManager;
import ureca.nolmung.implementation.dogdiary.DogDiaryManager;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.badgecode.BadgeCode;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.dogdiary.DogDiary;
import ureca.nolmung.jpa.media.Media;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;


@Service
@RequiredArgsConstructor
public class DiaryService implements DiaryUseCase {

    private final DiaryManager diaryManager;
    private final UserManager userManager;
    private final DiaryDtoMapper diaryDtoMapper;
    private final DogDiaryManager dogDiaryManager;
    private final DiaryPlaceManager diaryPlaceManager;
    private final BadgeManager badgeManager;

    public static final Long FIRST_DIARY_BADGE_CODE_ID = 1L;
    public static final Long THIRD_DIARY_BADGE_CODE_ID = 2L;

    @Override
    @Transactional
    public AddDiaryResp addDiary(User user, AddDiaryReq req) {
        // 유저 검증
        User loginUser = userManager.validateUserExistence(user.getId());
        loginUser.incrementDiaryCount();

        boolean firstBadgeAdded = false;
        boolean thirdBadgeAdded = false;

        if(!badgeManager.checkDiaryPostBadge(FIRST_DIARY_BADGE_CODE_ID, user.getId()))
        {
            if(loginUser.getDiaryCount() >= 1)
            {
                BadgeCode badgeCode = badgeManager.validateBadgeCodeExistence(FIRST_DIARY_BADGE_CODE_ID);
                badgeManager.addDiaryBadge(loginUser, badgeCode);
                firstBadgeAdded = true;
            }
        }

        if(!badgeManager.checkDiaryPostBadge(THIRD_DIARY_BADGE_CODE_ID, user.getId()))
        {
            if(loginUser.getDiaryCount() >= 3)
            {
                BadgeCode badgeCode = badgeManager.validateBadgeCodeExistence(THIRD_DIARY_BADGE_CODE_ID);
                badgeManager.addDiaryBadge(loginUser, badgeCode);
                thirdBadgeAdded = true;
            }
        }

        // 오늘 쓴 오늘멍이 존재하는지 확인
        diaryManager.checkTodayDiary(user.getId(),now());

        return diaryManager.addDiary(loginUser, req, firstBadgeAdded, thirdBadgeAdded);
    }

    @Override
    @Transactional(readOnly = true)
    public DiaryListResp getAllDiaries(User user) {
        // 유저 검증
        User loginUser = userManager.validateUserExistence(user.getId());
        List<Diary> diaryList = diaryManager.getDiaryList(loginUser.getId());
        return diaryDtoMapper.toAddDiaryResp(loginUser, diaryList);
    }


    @Override
    @Transactional(readOnly = true)
    public DiaryDetailResp getDetailDiary(Long diaryId) {
        // 다이어리 유효성 검증
        Diary diaryCheck = diaryManager.checkExistDiary(diaryId);
        return getDiaryDetailResp(diaryCheck);
    }

    @Override
    @Transactional(readOnly = true)
    public DiaryDetailResp getDetailMyDiary(User user, Long diaryId) {
        // 작성자 검증
        Diary diaryCheck = diaryManager.checkDiaryWriter(user.getId(), diaryId);
        return getDiaryDetailResp(diaryCheck);
    }

    private DiaryDetailResp getDiaryDetailResp(Diary diaryCheck) {
        Diary diary = diaryManager.getDetailDiary(diaryCheck.getId());
        // 함께한 반려견 리스트
        List<DogDiary> dogList = dogDiaryManager.getDogList(diaryCheck.getId());
        // 방문한 장소 리스트
        List<Place> placeList = diaryPlaceManager.getPlaceList(diaryCheck.getId());
        // 등록된 미디어 리스트
        List<Media> mediaList = diary.getMediaList();
        return diaryDtoMapper.toDiaryDetailResp(diary, dogList, placeList, mediaList);
    }


    @Override
    @Transactional
    public DeleteDiaryResp deleteDiary(User user, Long diaryId) {
        // 유저 검증
        User loginUser = userManager.validateUserExistence(user.getId());
        // 작성자 검증
        Diary diaryCheck = diaryManager.checkDiaryWriter(user.getId(), diaryId);

        // 일기 카운트 감소
        loginUser.decrementDiaryCount();

        return diaryManager.deleteDiary(diaryCheck);
    }

    @Override
    @Transactional
    public UpdateDiaryResp updateDiary(User user, Long diaryId, UpdateDiaryReq req) {
        // 작성자 검증
        Diary diaryCheck = diaryManager.checkDiaryWriter(user.getId(), diaryId);
        return diaryManager.updateDiary(diaryCheck, req);
    }
}
