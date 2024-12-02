package ureca.nolmung.business.banword;

public interface BanWordUseCase {

	public void init();

	public void saveBanWordsFromFile();

	public void loadBanWordTrie();

	public String checkBanWord(String text);

}
