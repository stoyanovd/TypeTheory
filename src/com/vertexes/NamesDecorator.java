package com.vertexes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created  by dima  on 22.10.14.
 */
public class NamesDecorator {

	private Set<String> freeProposes;
	private Map<String, String> intToName;
	private int curCount;
	public Vertex nice;
	public Vertex nice2;

	public NamesDecorator(Vertex v) {
		freeProposes = Vertex.getFreeProposes(v);
		intToName = new HashMap<>();
		nice = new Vertex(v);
		curCount = 0;

		makeNiceNames(nice);
	}

	public NamesDecorator(Vertex v, Vertex t) {
		freeProposes = Vertex.getFreeProposes(v);
		freeProposes.addAll(Vertex.getFreeProposes(t));
		intToName = new HashMap<>();
		nice = new Vertex(v);
		curCount = 0;
		makeNiceNames(nice);
		nice2 = new Vertex(t);
		makeNiceNames(nice2);
	}

	private void makeNiceNames(Vertex t) {

		if (t == null) {
			return;
		}
		switch (t.operation) {
			case 'V': {
				if (freeProposes.contains(t.propose)) {
					return;
				}
				if (!intToName.containsKey(t.propose)) {
					System.out.println("We don't know this propose: " + t.propose);
					throw new NullPointerException();
				}
				t.propose = intToName.get(t.propose);
				t.countHashAndCo();
				return;
			}
			case 'A': {
				makeNiceNames(t.left);
				makeNiceNames(t.right);
				t.countHashAndCo();
				return;
			}
			case 'L': {
				if (!intToName.containsKey(t.left.propose))                                                //   It is interesting place
				{
					String s;
					do {
						s = "x" + curCount;
						curCount++;
					}
					while (freeProposes.contains(s));
					intToName.put(t.left.propose, s);
				}
				makeNiceNames(t.left);
				makeNiceNames(t.right);
				t.countHashAndCo();
			}
		}
	}
}
