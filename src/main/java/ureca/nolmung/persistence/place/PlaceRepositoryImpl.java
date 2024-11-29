package ureca.nolmung.persistence.place;

import static ureca.nolmung.jpa.bookmark.QBookmark.*;
import static ureca.nolmung.jpa.place.Enum.Category.*;
import static ureca.nolmung.jpa.place.QPlace.*;

import java.util.List;

import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Place> findBySearchOption(Long userId, Category category, String acceptSize, Double ratingAvg, Boolean isBookmarked, Polygon polygon) {
		return queryFactory.selectFrom(place)
			.where(eqCategory(category),
				eqAcceptSize(acceptSize),
				eqRatingAvg(ratingAvg),
				isBookmarked(userId, isBookmarked),
				isWithinPolygon(polygon))
			.fetch();
	}

	private BooleanExpression eqAcceptSize(String acceptSize) {
		if (acceptSize == null || acceptSize.equals("ALL")) {
			return null;
		}
		return place.acceptSize.eq(acceptSize).or(place.acceptSize.eq("ALL"));
	}

	private BooleanExpression eqRatingAvg(Double ratingAvg) {
		if (ratingAvg == null) {
			return null;
		}
		return place.ratingAvg.goe(3.0).or(place.ratingAvg.goe(4.0));
	}

	private BooleanExpression eqCategory(Category category) {
		if (category == null || category.equals(ALL)) {
			return null;
		}
		return place.category.eq(category);
	}

	private BooleanExpression isBookmarked(Long userId, Boolean isBookmarked) {
		if (isBookmarked == null || !isBookmarked) {
			return null;
		}

		return place.id.in(
			JPAExpressions.select(bookmark.place.id)
				.from(bookmark)
				.where(bookmark.user.id.eq(userId))
		);
	}

	private BooleanExpression isWithinPolygon(Polygon polygon) {
		return Expressions.booleanTemplate("ST_Contains({0},{1})", polygon, place.placePosition.location);
	}

}
