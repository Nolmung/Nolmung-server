package ureca.nolmung.implementation.badge;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.implementation.dog.BadgeCodeException;
import ureca.nolmung.implementation.dog.BadgeCodeExceptionType;
import ureca.nolmung.jpa.badge.Badge;
import ureca.nolmung.jpa.badgecode.BadgeCode;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.badge.BadgeRepository;
import ureca.nolmung.persistence.badgecode.BadgeCodeRepository;

@Component
@RequiredArgsConstructor
public class BadgeManager {

    private final BadgeRepository badgeRepository;
    private final BadgeCodeRepository badgeCodeRepository;

    public BadgeCode validateBadgeCodeExistence(Long badgeCodeId) {
        return badgeCodeRepository.findById(badgeCodeId).orElseThrow(
            () -> new BadgeCodeException(BadgeCodeExceptionType.BADGE_CODE_NOT_FOUND_EXCEPTION)
        );
    }

    public List<Badge> getBadgeList(Long userId) {
        return badgeRepository.findByUserId(userId);
    }

    public void addDiaryBadge(User user, BadgeCode badgeCode)
    {
        Badge badge = new Badge(user, badgeCode);
        badgeRepository.save(badge);
    }

    public boolean checkDiaryPostBadge(Long badgeCodeId, Long userId) {
        return badgeRepository.existsByBadgeCodeIdAndUserId(badgeCodeId,userId);
    }

}
