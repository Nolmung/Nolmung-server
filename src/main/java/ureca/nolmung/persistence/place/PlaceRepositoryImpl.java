package ureca.nolmung.persistence.place;

import static ureca.nolmung.jpa.place.QPlace.*;

import java.util.List;

import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Place> findBySearchOption(Category category, String acceptSize, Double ratingAvg, Polygon polygon) {
		return queryFactory.selectFrom(place)
			.where(eqCategory(category),
				eqAcceptSize(acceptSize),
				eqRatingAvg(ratingAvg),
				isWithinPolygon(polygon))
			.fetch();
	}

	private BooleanExpression eqAcceptSize(String acceptSize) {
		if (acceptSize == null) {
			return null;
		}

		if (acceptSize.equals("ALL")) {
			return place.acceptSize.eq(acceptSize);
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
		if (category == null) {
			return null;
		}
		return place.category.eq(category);
	}

	private BooleanExpression isWithinPolygon(Polygon polygon) {
		return Expressions.booleanTemplate("ST_Contains({0},{1})", polygon, place.placePosition.location);
	}

}
