package dima.vertexes;

/**
 * Created  by dima  on 26.10.14.
 */
class DSUEntry {
	private DSUEntry next;
	private Vertex fullVertex;
	private Vertex cur;

	private DSUEntry(Vertex v) {
		next = this;
		fullVertex = null;
		cur = v;
	}

	public DSUEntry showNext() {
		return next;
	}

	public static void tryInitDSUEntry(Vertex v) {
		if (!MyCache.dsuMap.containsKey(v)) {
			MyCache.dsuMap.put(v, new DSUEntry(v));
		}
	}

	//Vertex is already in dsuMap
	//Fulls in dsuMap are untouchable.
	public static void setFull(Vertex v) {
		DSUEntry dsuEntry = MyCache.dsuMap.get(v);
		dsuEntry.simplify();
		if (Normalizer.hasRedex(v)) {
			throw new NullPointerException("We try to put to dsuMap vertex that is not fully reduced.");
		}
		dsuEntry.fullVertex = new Vertex(v);
	}

	public static void setFull(Vertex v, Vertex simplified) {
		DSUEntry dsuEntry = MyCache.dsuMap.get(v);
		dsuEntry.simplify();
		/*if (Normalizer.hasRedex(v)) {
			throw new NullPointerException("We try to put to dsuMap vertex that is not fully reduced.");
		}*/
		dsuEntry.fullVertex = new Vertex(simplified);
	}

	//Vertexes are already in dsuMap
	public static void union(Vertex current, Vertex toBeNext) {
		DSUEntry x = MyCache.dsuMap.get(current);
		DSUEntry y = MyCache.dsuMap.get(toBeNext);
		x.simplify();
		y.simplify();
		x.next = y;
		x.simplify();
	}

	//	Vertex is already in dsuMap
	public static Vertex getNewFullVertex(Vertex v) {
		DSUEntry dsuEntry = MyCache.dsuMap.get(v);
		dsuEntry.simplify();
		if (dsuEntry.fullVertex == null) {
			return null;
		}
		return new Vertex(dsuEntry.fullVertex);
	}

	//	Vertex is already in dsuMap
	public static Vertex getNewNextVertex(Vertex v) {
		DSUEntry dsuEntry = MyCache.dsuMap.get(v);
		dsuEntry.simplify();
		if (dsuEntry.next == dsuEntry) {
			return null;
		}
		return new Vertex(dsuEntry.next.cur);
	}

	//	Vertex is already in dsuMap.
	public static boolean hasFullVertex(Vertex v) {
		DSUEntry dsuEntry = MyCache.dsuMap.get(v);
		dsuEntry.simplify();
		return (dsuEntry.fullVertex != null);
	}

	//	Vertex is already in dsuMap
	public static boolean hasNextVertex(Vertex v) {
		DSUEntry dsuEntry = MyCache.dsuMap.get(v);
		dsuEntry.simplify();
		return dsuEntry.next != dsuEntry;
	}

	private void simplify() {
		//System.out.println((v.next == null) + " " + (v.next.next == null));
		if (next.next != next) {
			next.simplify();
		}
		next = next.next;
		if (fullVertex != null && next.fullVertex == null) {
			throw new NullPointerException("We make it bad.");        //TODO  reformat this
		}
		fullVertex = next.fullVertex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		DSUEntry dsuEntry = (DSUEntry) o;

		if (!cur.equals(dsuEntry.cur)) {
			return false;
		}
		if (!fullVertex.equals(dsuEntry.fullVertex)) {
			return false;
		}
		if (!next.equals(dsuEntry.next)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = next.hashCode();
		result = 31 * result + fullVertex.hashCode();
		result = 31 * result + cur.hashCode();
		return result;
	}
}
