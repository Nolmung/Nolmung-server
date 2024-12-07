package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.jpa.place.Place;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RedisManager {

    private final RedisTemplate<String, List<RecommendResp>> redisTemplate;

    public List<RecommendResp> getRedis(String redisKey) {
        return redisTemplate.opsForValue().get(redisKey);
    }

    public List<RecommendResp> saveRedis(List<Place> places, String redisKey) {
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

        redisTemplate.opsForValue().set(redisKey, recommendResponses);

        return recommendResponses;
    }

}
