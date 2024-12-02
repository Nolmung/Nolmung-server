package ureca.nolmung.implementation.badge.dtomapper;

import org.springframework.stereotype.Component;
import ureca.nolmung.business.badge.dto.response.BadgeResp;
import ureca.nolmung.jpa.badge.Badge;

@Component
public class BadgeDtoMapper {
    public BadgeResp toBadgeResp(Long userId, Badge badge) {
        return new BadgeResp(userId,
                badge.getBadgeCode().getId(),
                badge.getBadgeCode().getBadgeName(),
                badge.getBadgeCode().getContent(),
                badge.getBadgeCode().getBadgeImageUrl());
    }
}
