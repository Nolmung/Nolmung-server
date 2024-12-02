package ureca.nolmung.business.banword;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ureca.nolmung.implementation.banword.BanWordManager;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BanWordService implements BanWordUseCase {

	private final BanWordManager banWordManager;

	@Override
	@PostConstruct
	public void init() {
		banWordManager.loadBanWordTrie();
	}

	@Override
	@Transactional
	public void saveBanWordsFromFile() {
		String filePath = "C:/Users/KoKyungNam/Desktop/nolmung/banword.txt";
		banWordManager.loadBanWordsFromFile(filePath);
	}

}
