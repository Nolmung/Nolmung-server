package ureca.nolmung.business.badge;

import java.util.List;

import ureca.nolmung.business.badge.dto.response.BadgeResp;

public interface BadgeUseCase {

    List<BadgeResp> getBadge(Long userId);
}
