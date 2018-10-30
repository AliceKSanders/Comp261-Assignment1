import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {

	private char charac;
	private boolean wordEnding;
	// holds children of node
	// hashMap
	public Map<Character, TrieNode> childrenNode = new HashMap<>();

	//For the last elem
	//Differnece in number of arguments
	public TrieNode(char charac, boolean we) {
		this.charac = charac;
		this.wordEnding = we;
	}

	public TrieNode(char charac, Map<Character, TrieNode> childrenNode, boolean we) {
		this.charac = charac;
		this.wordEnding = we;
		this.childrenNode = childrenNode;
	}

	// Create a new Node
	public TrieNode add(char c, boolean we) {
		 TrieNode newChild = new TrieNode(c, we);
		 childrenNode.put(c, newChild);
		 return newChild;
	}

	public TrieNode get(char childNode) {
		return childrenNode.get(childNode);
	}

	public boolean isWordEnding() {
		return wordEnding;
	}

	public void setWordEnding(boolean wordEnding) {
		this.wordEnding = wordEnding;
	}

	public char getCharac() {
		return charac;
	}

	public void setCharac(char charac) {
		this.charac = charac;
	}

}
