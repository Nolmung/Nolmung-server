package ureca.nolmung.persistence.bookmark;

import static ureca.nolmung.jpa.bookmark.QBookmark.*;
import static ureca.nolmung.jpa.place.Enum.Category.*;
import static ureca.nolmung.jpa.place.QPlace.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.user.User;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Bookmark> findByUserAndCategory(User user, Category category) {
		return queryFactory.selectFrom(bookmark)
			.where(eqUser(user), eqCategory(category))
			.orderBy(bookmark.createdAt.desc())
			.fetch();
	}

	private BooleanExpression eqUser(User user) {
		if (user == null) {
			return null;
		}
		return bookmark.user.eq(user); // 북마크의 유저와 비교
	}

	private BooleanExpression eqCategory(Category category) {
		if (category == null || category.equals(ALL)) {
			return null;
		}
		return place.category.eq(category);
	}

}
