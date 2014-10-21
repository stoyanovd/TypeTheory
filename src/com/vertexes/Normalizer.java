package com.vertexes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 01.10.14.
 */
public class Normalizer {

	public Vertex ans;
	private HashMap<Vertex, Vertex> doneMap;
	private HashMap<Vertex, Integer> dfsMap;

	public Normalizer(Vertex v) {
		doneMap = new HashMap<>();
		dfsMap = new HashMap<>();

		ans = new Vertex(v);
		ans = getNorm(ans);

		System.out.println("ans:");
		System.out.println(ans.toString());

		NamesDecorator namesDecorator = new NamesDecorator(ans);
		ans = namesDecorator.nice;

		System.out.println("nice:");
		System.out.println(ans.toString());
		System.out.println(dfs_findRedexes(ans));
	}

	private boolean hasRedex(Vertex t) {
		return (dfs_findRedexes(t) != -1);
	}

	private int dfs_findRedexes(Vertex t) {
		if (t == null) {
			return -1;
		}
		if (t.isRedex()) {
			return 0;
		}
		if (dfsMap.containsValue(t)) {
			return dfsMap.get(t);
		}

		int x = -1;
		if (t.left != null) {
			x = dfs_findRedexes(t.left);
		}
		if (x != -1) {
			x++;
		}

		int y = -1;
		if (t.right != null) {
			y = dfs_findRedexes(t.right);
		}
		if (y != -1) {
			y++;
		}

		int a;
		if (x == -1 && y == -1) {
			a = -1;
		} else if (x != -1 && y != -1) {
			a = Math.min(x, y);
		} else {
			a = (-x * y);
		}
		dfsMap.put(t, a);
		return a;
	}

	private void innerUnify(Vertex v, Map<String, Integer> map, int i) {
		if (v == null) {
			return;
		}
		switch (v.operation) {
			case 'V': {
				if (map.containsKey(v.propose)) {
					v.propose = "-" + map.get(v.propose);                                    //to   step
				}
				break;
			}
			case 'A': {
				innerUnify(v.left, map, i);
				innerUnify(v.right, map, i);
				break;
			}
			case 'L': {
				map.put(v.left.propose, i);
				innerUnify(v.right, map, i + 1);
				map.remove(v.left.propose);
				v.left.propose = "-" + i;                                //to   step
			}
		}
		v.countHashAndCo();
	}

	private Vertex reductRedex(Vertex redex) {
		NamesDecorator namesDecorator = new NamesDecorator(redex.left, redex.right);
		Vertex a = namesDecorator.nice.right;
		Vertex b = namesDecorator.nice2;
		Substitution substitution = new Substitution(namesDecorator.nice.left.propose, b);
		if (!substitution.makeSubstitution(a)) {
			System.out.println("This not nice error with same names.\n\n");
			throw new NullPointerException();
		}
		return substitution.changedVertex;
	}

	private Vertex getNorm(Vertex v) {

		System.out.println("getNorm, begin: " + v.toString());
		System.out.println("dfs " + dfs_findRedexes(v));

		if (!hasRedex(v)) {                                                            //TODO-speed    Maybe it is nice to play with maps
			return v;
		}

		if (doneMap.containsValue(v)) {
			return doneMap.get(v);
		}

		if (dfs_findRedexes(v) == 0) {
			Vertex t = reductRedex(v);
			System.out.println("Reduct from : " + v.toString());
			System.out.println("To : " + t.toString());
			Vertex p = getNorm(t);
			doneMap.put(v, p);
			doneMap.put(t, p);
			return p;
		}

		Vertex t = new Vertex();
		t.operation = v.operation;
		t.left = v.left;
		t.right = v.right;
		int x = dfs_findRedexes(v.left);
		int y = dfs_findRedexes(v.right);
		if (x != -1 && (y == -1 || x < y)) {
			t.left = getNorm(v.left);
		} else {
			t.right = getNorm(v.right);
		}
		t.countHashAndCo();
		t = getNorm(t);
		doneMap.put(v, t);
		return t;
	}
}
