package ureca.nolmung.persistence.place;

import static ureca.nolmung.jpa.bookmark.QBookmark.*;
import static ureca.nolmung.jpa.place.Enum.Category.*;
import static ureca.nolmung.jpa.place.QPlace.*;

import java.util.List;
import java.util.Set;

import com.querydsl.core.BooleanBuilder;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.place.QPlace;
import ureca.nolmung.jpa.user.User;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Place> findBySearchOption(User user, Category category, String acceptSize, Double ratingAvg, Boolean isBookmarked, Polygon polygon) {
		return queryFactory.selectFrom(place)
			.where(eqCategory(category),
				eqAcceptSize(acceptSize),
				eqRatingAvg(ratingAvg),
				isBookmarked(user, isBookmarked),
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

	private BooleanExpression isBookmarked(User user, Boolean isBookmarked) {
		if (isBookmarked == null || !isBookmarked || user == null) {
			return null;
		}

		return place.id.in(
			JPAExpressions.select(bookmark.place.id)
				.from(bookmark)
				.where(bookmark.user.eq(user))
		);
	}

	private BooleanExpression isWithinPolygon(Polygon polygon) {
		return Expressions.booleanTemplate("ST_Contains({0},{1})", polygon, place.placePosition.location);
	}

}
