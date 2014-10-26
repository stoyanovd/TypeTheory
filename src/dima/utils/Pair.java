package dima.utils;

/**
 * Created  by dima  on 15.10.14.
 */
public class Pair<K, V> {

	public K f;
	public V s;

	public Pair(K f, V s) {
		this.f = f;
		this.s = s;
	}

	@Override
	public int hashCode() {
		int result = f.hashCode();
		result = 17239 * result + s.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return obj.getClass().equals(Pair.class) && (f.equals(((Pair) obj).f) && s.equals(((Pair) obj).s));
	}

	@Override
	public String toString() {
		return "Pair{" +
				"f=" + f +
				"\ns=" + s +
				'}';
	}
}
