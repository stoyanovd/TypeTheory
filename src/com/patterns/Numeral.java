package com.patterns;

import com.vertexes.Vertex;

/**
 * Created  by dima  on 22.10.14.
 */
public class Numeral {

	public static Integer getInteger(Vertex v) {
		if (v == null || v.operation != 'L' || v.right.operation != 'L') {
			return null;
		}
		String s = v.left.propose;
		String z = v.right.left.propose;

		int i = 0;
		Vertex t = v.right.right;
		while (true) {
			if (t == null) {
				return null;
			}
			if (t.propose != null && t.propose.equals(z)) {
				return i;
			}
			if (t.operation == 'A' && t.left.propose != null && t.left.propose.equals(s)) {
				i++;
				t = t.right;
				continue;
			}
			return null;
		}
	}
}
