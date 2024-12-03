package ureca.nolmung.implementation.badge;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.jpa.badge.Badge;
import ureca.nolmung.jpa.badgecode.BadgeCode;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.badge.BadgeRepository;
import ureca.nolmung.persistence.badgecode.BadgeCodeRepository;

@Component
@RequiredArgsConstructor
public class BadgeManager {

    public static final Long FIRST_DIARY_BADGE_CODE_ID = 1L;
    public static final Long THIRD_DIARY_BADGE_CODE_ID = 2L;

    private final BadgeRepository badgeRepository;
    private final BadgeCodeRepository badgeCodeRepository;


    public List<Badge> getBadgeList(Long userId) {
        return badgeRepository.findByUserId(userId);
    }

    public void addFirstDiaryBadge(User user)
    {
        Optional<BadgeCode> badgeCode = badgeCodeRepository.findById(FIRST_DIARY_BADGE_CODE_ID);

        if (badgeCode.isPresent())
        {
            Badge badge = new Badge(user, badgeCode.get());
            badgeRepository.save(badge);
        }
    }

    public void addThirdDiaryBadge(User user)
    {
        Optional<BadgeCode> badgeCode = badgeCodeRepository.findById(THIRD_DIARY_BADGE_CODE_ID);

        if (badgeCode.isPresent())
        {
            Badge badge = new Badge(user, badgeCode.get());
            badgeRepository.save(badge);
        }
    }

    public boolean checkFirstDiaryPostBadge(Long userId) {

        Optional<Badge> firstDiaryPostBadge = badgeRepository.findByBadgeCodeIdAndUserId(FIRST_DIARY_BADGE_CODE_ID,userId);

        if(firstDiaryPostBadge.isPresent())
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean checkThirdDiaryPostBadge(Long userId) {

        Optional<Badge> thirdDiaryPostBadge = badgeRepository.findByBadgeCodeIdAndUserId(THIRD_DIARY_BADGE_CODE_ID,userId);

        if(thirdDiaryPostBadge.isPresent())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
