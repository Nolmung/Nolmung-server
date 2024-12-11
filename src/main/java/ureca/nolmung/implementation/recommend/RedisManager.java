package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.jpa.place.Place;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RedisManager {

    private final RedisTemplate<String, List<RecommendResp>> redisTemplate;

    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    public void addExpire(String key, int hour, TimeUnit timeUnit) {
        redisTemplate.expire(key, hour, timeUnit);
    }

    public void boundValueOps(String key, List<RecommendResp> recommendResps, int hour, TimeUnit timeUnit) {
        redisTemplate.boundValueOps(key)
                .set(recommendResps, hour, timeUnit);
    }

    public List<RecommendResp> getRedis(Long userId) {
        return redisTemplate.opsForValue().get(String.valueOf(userId));
    }

    public List<RecommendResp> saveRedis(List<Place> places, String key) {
        List<RecommendResp> recommendResponses = places.stream()
                .map(place -> new RecommendResp(
                        place.getId(),
                        place.getName(),
                        place.getCategory(),
                        place.getRoadAddress(),
                        place.getAddress(),
                        place.getPlaceImageUrl(),
                        place.getRatingAvg(),
                        place.getRatingCount()
                ))
                .collect(Collectors.toList());

        boundValueOps(key, recommendResponses, 48, TimeUnit.HOURS);

        return recommendResponses;
    }
}
