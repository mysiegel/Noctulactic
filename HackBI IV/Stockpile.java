package spacegame;

import java.util.HashMap;

public class Stockpile {
	HashMap<String, Integer> internal;

	public Stockpile() {
		internal = new HashMap<String, Integer>();
		internal.put("ore", 0);
		internal.put("alloy", 0);
		// internal.put("spoil", 0);
		// internal.put("fuel", 0);
		internal.put("food", 0);
	}

	public int get(String t) {
		return internal.get(t);
	}

	public void modify(String t, int x) {
		internal.replace(t, internal.get(t) + x);
	}

	public void set(String t, int x) {
		internal.replace(t, x);
	}

	public int modifyAndGet(String t, int x) {
		modify(t, x);
		return get(t);
	}

	public String toString() {
		String ret = "";

		MutableString s = new MutableString();

		internal.forEach((resource, amount) -> {
			s.add(resource + amount + ",");
		});

		ret += s.toString();

		return ret;
	}
}