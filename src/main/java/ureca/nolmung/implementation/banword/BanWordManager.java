package ureca.nolmung.implementation.banword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.jpa.banword.BanWord;
import ureca.nolmung.jpa.banword.trie.Trie;
import ureca.nolmung.persistence.banword.BanWordRepository;

@RequiredArgsConstructor
@Component
public class BanWordManager {

	private final BanWordRepository banWordRepository;
	private final Trie trie;

	public void loadBanWordTrie() {
		List<BanWord> banWords = banWordRepository.findAll();
		banWords.forEach(banWord -> insertBanWordIntoTrie(banWord));
		trie.buildFailureLinks();
	}

	private void insertBanWordIntoTrie(BanWord banWord) {
		trie.insert(banWord.getWord());
	}

	public void loadBanWordsFromFile(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String word;
			while ((word = br.readLine()) != null) {
				saveBanWord(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveBanWord(String word) {
		BanWord banWord = BanWord.builder()
			.word(word)
			.build();
		banWordRepository.save(banWord);
	}

}
