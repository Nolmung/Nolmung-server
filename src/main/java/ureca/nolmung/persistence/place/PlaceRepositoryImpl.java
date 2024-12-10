package ureca.nolmung.persistence.place;

import static ureca.nolmung.jpa.bookmark.QBookmark.*;
import static ureca.nolmung.jpa.place.Enum.Category.*;
import static ureca.nolmung.jpa.place.QPlace.*;
import static ureca.nolmung.jpa.placeposition.QPlacePosition.*;
import static ureca.nolmung.jpa.review.QReview.*;

import java.util.List;
import java.util.Set;

import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.place.QPlace;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Place> findBySearchOption(CustomUserDetails userDetails, Category category, Boolean isVisited,
		Boolean isBookmarked, Polygon polygon) {
		return queryFactory.selectFrom(place)
			.where(eqCategory(category),
				isVisited(userDetails, isVisited),
				isBookmarked(userDetails, isBookmarked),
				isWithinPolygon(polygon))
			.fetch();
	}

	public List<Place> findAllByDogSizes(Set<String> sizes) {
		QPlace place = QPlace.place;

		BooleanBuilder builder = new BooleanBuilder();

		for (String size : sizes) {
			builder.or(place.acceptSize.like("%" + size + "%"));
		}
		builder.or(place.acceptSize.eq("ALL"));

		return queryFactory.selectFrom(place)
			.where(builder)
			.orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
			.limit(5)
			.fetch();
	}

	private BooleanExpression eqCategory(Category category) {
		if (category == null || category.equals(ALL)) {
			return null;
		}
		return place.category.eq(category);
	}

	private BooleanExpression isVisited(CustomUserDetails userDetails, Boolean isVisited) {
		if (isVisited == null || !isVisited || userDetails == null) {
			return null;
		}

		return place.id.in(
			JPAExpressions.select(review.place.id)
				.from(review)
				.where(review.user.eq(userDetails.getUser()))
		);
	}

	private BooleanExpression isBookmarked(CustomUserDetails userDetails, Boolean isBookmarked) {
		if (isBookmarked == null || !isBookmarked || userDetails == null) {
			return null;
		}

		return place.id.in(
			JPAExpressions.select(bookmark.place.id)
				.from(bookmark)
				.where(bookmark.user.eq(userDetails.getUser()))
		);
	}

	private BooleanExpression isWithinPolygon(Polygon polygon) {
		return Expressions.booleanTemplate("ST_Contains({0},{1})",
			polygon,
			JPAExpressions.select(placePosition.location)
				.from(placePosition)
				.where(placePosition.place.id.eq(place.id))
		);
	}

}
