package ureca.nolmung.business.banword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ureca.nolmung.jpa.banword.BanWord;
import ureca.nolmung.jpa.banword.trie.Trie;
import ureca.nolmung.jpa.banword.trie.TrieNode;
import ureca.nolmung.persistence.banword.BanWordRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BanWordService implements BanWordUseCase{

	private final BanWordRepository banWordRepository;

	private final Trie trie = new Trie();

	@Override
	@PostConstruct
	public void init() {
		loadBanWordTrie();
	}

	@Override
	@Transactional
	public void saveBanWordsFromFile() {
		String filePath = "C:/Users/KoKyungNam/Desktop/nolmung/banword.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				BanWord banWord = BanWord.builder()
					.word(line)
					.build();
				banWordRepository.save(banWord);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadBanWordTrie() {
		List<BanWord> banWords = banWordRepository.findAll();
		for (BanWord banWord : banWords) {
			trie.insert(banWord.getWord());
		}
	}

	@Override
	public String checkBanWord(String text) {
		int textLength = text.length();

		for (int i = 0; i < textLength; i++) {
			TrieNode current = trie.getRoot(); // 트라이의 루트 노드에서 시작
			for (int j = i; j < textLength; j++) {
				char c = text.charAt(j); // 현재 문자
				current = current.children.get(c); // 트라이에서 현재 문자를 탐색
				if (current == null) {
					break; // 더 이상 탐색할 수 없는 경우
				}
				if (current.isEndOfWord) {
					return text.substring(i, j + 1); // 금칙어 발견
				}
			}
		}
		return null; // 금칙어 없음
	}

}
