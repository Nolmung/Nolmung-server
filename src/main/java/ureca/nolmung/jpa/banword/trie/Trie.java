package ureca.nolmung.jpa.banword.trie;

public class Trie {

	private final TrieNode root;

	public Trie() {
		root = new TrieNode();
	}

	public TrieNode getRoot() {
		return root;
	}

	public void insert(String word) {
		TrieNode current = root;
		for (char c : word.toCharArray()) {
			current = current.children.computeIfAbsent(c, k -> new TrieNode());
		}
		current.isEndOfWord = true;
	}

	public boolean search(String word) {
		TrieNode current = root;
		for (char c : word.toCharArray()) {
			current = current.children.get(c);
			if (current == null) {
				return false;
			}
		}
		return current.isEndOfWord;
	}

}
