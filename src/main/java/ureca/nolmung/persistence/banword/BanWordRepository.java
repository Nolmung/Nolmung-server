package ureca.nolmung.persistence.banword;

import org.springframework.data.jpa.repository.JpaRepository;

import ureca.nolmung.jpa.banword.BanWord;

public interface BanWordRepository extends JpaRepository<BanWord, Long> {
}
