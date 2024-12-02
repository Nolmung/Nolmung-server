package ureca.nolmung.jpa.banword.trie;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Component;

@Component
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

	public void buildFailureLinks() {
		Queue<TrieNode> queue = new LinkedList<>();
		root.failLink = root;
		queue.add(root);

		while (!queue.isEmpty()) {
			TrieNode currentNode = queue.poll();

			for (Map.Entry<Character, TrieNode> entry : currentNode.children.entrySet()) {
				char c = entry.getKey();
				TrieNode child = entry.getValue();

				if (currentNode == root) {
					child.failLink = root;
				} else {
					TrieNode fallback = currentNode.failLink;

					while (fallback != root && !fallback.children.containsKey(c)) {
						fallback = fallback.failLink;
					}

					if (fallback.children.containsKey(c)) {
						fallback = fallback.children.get(c);
					}

					child.failLink = fallback;
				}

				if (child.failLink.isEndOfWord) {
					child.isEndOfWord = true;
				}

				queue.add(child);
			}
		}
	}

	public boolean search(String word) {
		TrieNode current = root;
		for (char c : word.toCharArray()) {
			while (current != root && !current.children.containsKey(c)) {
				current = current.failLink; // 실패 링크를 따라감
			}

			if (current.children.containsKey(c)) {
				current = current.children.get(c);
			}

			if (current.isEndOfWord) {
				return true; // 단어가 발견됨
			}
		}
		return false;
	}

}
