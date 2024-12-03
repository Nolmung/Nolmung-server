package ureca.nolmung.persistence.badgecode;

import org.springframework.data.jpa.repository.JpaRepository;

import ureca.nolmung.jpa.badgecode.BadgeCode;

public interface BadgeCodeRepository extends JpaRepository<BadgeCode, Long> {
}
