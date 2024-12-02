package ureca.nolmung.implementation.badge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.badge.Badge;
import ureca.nolmung.persistence.badge.BadgeRepository;

import java.util.List;
@Component
@RequiredArgsConstructor
public class BadgeManager {

    private final BadgeRepository badgeRepository;
    public List<Badge> getBadgeList(Long userId) {
        return badgeRepository.findByUserId(userId);
    }
}
