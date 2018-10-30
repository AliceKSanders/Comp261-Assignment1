import java.util.*;

public class RoadTrie {

	private final TrieNode root;

	public RoadTrie() {
		// Set root of trie;
		root = new TrieNode('-', false);

	}

	public void add(String word) {
		//
		// //Set node to the root of the trie;
		TrieNode node = root;
		TrieNode childNode = null;
		for (int i = 0; i < word.length(); i++) {
			char child = word.charAt(i);

			if (!node.childrenNode.containsKey(child)) {
				if (i == word.length() - 1) {
					childNode = node.add(child, true);
				} else {
					childNode = node.add(child, false);
				}

			}

			// create a new child of node, connecting to node via c
			// move node to the child corresponding to c;

			if (childNode == null) {
				node = node.get(child);
			} else {
				node = childNode;
			}

			// add obj into node.objects;
		}

	}

	public TrieNode get(String getString) {
		// //Set node to the root of the trie;
		TrieNode node = root;
		for (int i = 0; i < getString.length(); i++) {
			char c = getString.charAt(i);

			node = node.get(c);
			if (node == null) {
				return null;
			}
		}

		return node;

	}

	public List<String> getAll(String getAllChildren) {
		List<String> results = new ArrayList<>();
		// Set node to the root of the trie;
		TrieNode node = this.get(getAllChildren);
		
		for (TrieNode child : node.childrenNode.values()) {
			if (child.isWordEnding()) {
				results.add(getAllChildren + Character.toString(child.getCharac()));
				if (!child.childrenNode.isEmpty()) {
					getAllFrom(child, getAllChildren + Character.toString(child.getCharac()), results);
				}
			}
			else {
				results = getAllFrom(child, getAllChildren + Character.toString(child.getCharac()), results);
			}
		}
		
		return results;
	}

	public List<String> getAllFrom(TrieNode node, String prefix, List<String> results) {
		// add node.objects into results;
		for (TrieNode child : node.childrenNode.values()) {
			if (child.isWordEnding()) {
				results.add(prefix + Character.toString(child.getCharac()));
				if (!child.childrenNode.isEmpty()) {
					getAllFrom(child, prefix + Character.toString(child.getCharac()), results);
				}
			}
			else {
				results = getAllFrom(child, prefix + Character.toString(child.getCharac()), results);
			}
		}
		
		return results;
	}
}
