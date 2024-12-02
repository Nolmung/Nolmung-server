package ureca.nolmung.business.badge;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.badge.dto.response.BadgeResp;
import ureca.nolmung.implementation.badge.BadgeManager;
import ureca.nolmung.implementation.badge.dtomapper.BadgeDtoMapper;

@Service
@RequiredArgsConstructor
public class BadgeService implements BadgeUseCase{

    private final BadgeManager badgeManager;
    private final BadgeDtoMapper badgeDtoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BadgeResp> getBadge(Long userId) {
        return badgeManager.getBadgeList(userId).stream()
                .map(badge -> badgeDtoMapper.toBadgeResp(userId, badge))  // Dog 객체를 DogResp로 변환
                .collect(Collectors.toList());
    }
}
