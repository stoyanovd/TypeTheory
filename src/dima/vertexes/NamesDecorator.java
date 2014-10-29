package dima.vertexes;

import dima.supports.DebugInformer;
import dima.utils.Pair;

import java.util.HashMap;
import java.util.Set;

/**
 * Created  by dima  on 22.10.14.
 */
public class NamesDecorator {

	private Vertex nice;
	private Vertex nice2;
	private Set<String> freeProposes;
	private int curCount;

	public NamesDecorator(Vertex v) {
		DebugInformer.namesDecoratorCounts++;
		if (MyCache.niceMap.containsKey(v)) {
			DebugInformer.namesDecoratorCountsMap++;
			nice = MyCache.niceMap.get(v);
			return;
		}
		freeProposes = Vertex.getFreeProposes(v);
		nice = new Vertex(v);
		curCount = 0;

		makeNiceNames(nice, new HashMap<>());
		MyCache.niceMap.put(v, nice);
	}

	public NamesDecorator(Vertex v, Vertex t) {
		DebugInformer.namesDecoratorPairsCounts++;
		if (MyCache.nicePairsMap.containsKey(new Pair<>(v, t))) {
			DebugInformer.namesDecoratorPairsCountsMap++;
			Pair<Vertex, Vertex> pair = MyCache.nicePairsMap.get(new Pair<>(v, t));
			nice = pair.f;
			nice2 = pair.s;
			return;
		}
		freeProposes = Vertex.getFreeProposes(v);
		freeProposes.addAll(Vertex.getFreeProposes(t));
		nice = new Vertex(v);
		curCount = 0;
		makeNiceNames(nice, new HashMap<>());
		nice2 = new Vertex(t);
		makeNiceNames(nice2, new HashMap<>());
		MyCache.nicePairsMap.put(new Pair<>(v, t), new Pair<>(nice, nice2));
	}

	public Vertex getNewNice() {
		return new Vertex(nice);
	}

	public Vertex getNewNice2() {
		return new Vertex(nice2);
	}

	public boolean nicesAreEquals() {
		return nice.equals(nice2);
	}

	private void makeNiceNames(Vertex t, HashMap<String, String> intToName) {

		if (t == null) {
			return;
		}
		switch (t.operation) {
			case 'V': {
				if (intToName.containsKey(t.propose)) {
					t.propose = intToName.get(t.propose);
					t.countHashAndCo(false);
					return;
				}
				if (!freeProposes.contains(t.propose)) {
					throw new NullPointerException("We don't know this propose: " + t.propose);
				}
			}
			case 'A': {
				makeNiceNames(t.left, intToName);
				makeNiceNames(t.right, intToName);
				t.countHashAndCo(false);
				return;
			}
			case 'L': {
				String oldKey = t.left.propose;
				String oldValue = null;
				if (intToName.containsKey(oldKey)) {
					oldValue = intToName.get(oldKey);
				}

				String s;
				do {
					s = "x" + curCount;
					curCount++;
				}
				while (freeProposes.contains(s));
				intToName.put(oldKey, s);

				makeNiceNames(t.left, intToName);
				makeNiceNames(t.right, intToName);
				t.countHashAndCo(false);
				if (oldValue == null) {
					intToName.remove(oldKey);
				} else {
					intToName.put(oldKey, oldValue);
				}
			}
		}
	}
}
