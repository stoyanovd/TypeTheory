package com.vertexes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 01.10.14.
 */
public class Normalizer {

	private HashMap<Vertex, Vertex> done;
	public Vertex ans;
	private int step = 0;

	public Normalizer(Vertex v) {
		done = new HashMap<>();
		step = 0;
		ans = new Vertex(v);
		ans = getNorm(ans);
	}

	private Vertex unifyNames(Vertex v) {
		step++;
		Vertex t = new Vertex(v);
		innerUnify(t, new HashMap<>(), 0);
		return t;
	}

	private void innerUnify(Vertex v, Map<String, Integer> map, int i) {
		if (v == null) {
			return;
		}
		switch (v.operation) {
			case 'V': {
				if (map.containsKey(v.propose)) {
					v.propose = "-" + step + "-" + map.get(v.propose);
				}
				break;
			}
			case 'A': {
				innerUnify(v.left, map, i);
				innerUnify(v.right, map, i);
				break;
			}
			case 'L': {
				Map<String, Integer> nmap = new HashMap<>(map);
				nmap.put(v.left.propose, i);
				v.left.propose = "-" + step + "-" + i;
				innerUnify(v.right, nmap, i + 1);
			}
		}
	}

	private static int dfs_findRedexes(Vertex t) {
		if (t == null) {
			return -1;
		}
		if (t.isRedex()) {
			return 0;
		}
		int x = -1;
		if (t.left != null) {
			x = dfs_findRedexes(t.left);
		}
		int y = -1;
		if (t.right != null) {
			y = dfs_findRedexes(t.right);
		}
		if (x == -1 && y == -1) {
			return -1;
		}
		if (x != -1 && y != -1) {
			return Math.min(x, y);
		}
		return (-x * y);
	}

	private static boolean hasRedex(Vertex t) {
		return (dfs_findRedexes(t) != -1);
	}

	private Vertex reductRedex(Vertex redex) {
		Vertex a = unifyNames(redex.left.right);
		Vertex b = unifyNames(redex.right);
		Substitution substitution = new Substitution(redex.left.left.propose, b);
		if (!substitution.makeSubstitution(a)) {
			System.out.println("This not nice error with same names.\n\n");
			throw new NullPointerException();
		}
		return substitution.changedVertex;
	}

	private Vertex getNorm(Vertex v) {

		if (!hasRedex(v)) {
			return v;
		}

		if (dfs_findRedexes(v) == 0) {
			if (done.containsKey(v)) {
				return done.get(v);
			}
			Vertex t = reductRedex(v);
			done.put(v, t);
			return t;
		}

		if (dfs_findRedexes(v.left) <= dfs_findRedexes(v.right)) {
			Vertex t = new Vertex();
			t.operation = v.operation;
			t.left = getNorm(v.left);
			t.right = v.right;
			t.countHashAndCo();
			return getNorm(t);
		}
		Vertex t = new Vertex();
		t.operation = v.operation;
		t.left = v.left;
		t.right = getNorm(v.right);
		t.countHashAndCo();
		return getNorm(t);
	}
}
