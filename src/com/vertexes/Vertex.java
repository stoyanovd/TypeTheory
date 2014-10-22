package com.vertexes;

import com.patterns.Numeral;
import com.supports.Initials;
import com.utils.Equality;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created  by dima on 09.09.14.
 */
public class Vertex {

	public char operation;    /*	L : for lambda
							V : for variable
       						A : for application
       						F : for functions
       						N : for null
      					*/
	public String propose;

	public Vertex left;
	public Vertex right;

	public ArrayList<Vertex> args;

	public long hash;
	public int deep;

	public Vertex() {
		operation = 'N';
		propose = null;
		left = null;
		right = null;
		args = null;
		hash = 0;
		deep = 0;
	}

	public Vertex(Vertex v) {
		this.operation = v.operation;
		if (v.propose == null) {
			this.propose = null;
		} else {
			this.propose = v.propose;
		}
		this.left = copyVertex(v.left);
		this.right = copyVertex(v.right);
		if (v.args == null) {
			this.args = null;
		} else {
			this.args = new ArrayList<>(v.args);
		}
		countHashAndCo(false);
	}

	public static Set<String> getFreeProposes(Vertex v) {

		Set<String> ans = new HashSet<>();
		getFreeProposesInner(v, new HashSet<>(), ans);
		return ans;
	}

	private static void getFreeProposesInner(Vertex v, Set<String> bounded, Set<String> ansNames) {

		switch (v.operation) {
			case 'N':
				throw new NullPointerException();                //  or null,  maybe????

			case 'L': {
				boolean was = bounded.contains(v.left.propose);
				if (!was) {
					bounded.add(v.left.propose);
				}
				getFreeProposesInner(v.right, bounded, ansNames);
				if (!was) {
					bounded.remove(v.left.propose);
				}
				return;
			}
			case 'A': {
				getFreeProposesInner(v.left, bounded, ansNames);
				getFreeProposesInner(v.right, bounded, ansNames);
				return;
			}

			case 'V': {
				if (!bounded.contains(v.propose)) {
					ansNames.add(v.propose);
				}
			}
		}
	}

	//  We supposed a, b - with correct hash
	public static Vertex makeApplication(Vertex a, Vertex b) {
		Vertex v = new Vertex();
		v.operation = 'A';
		v.left = a;
		v.right = b;
		v.countHashAndCo(false);
		return v;
	}

	//  We supposed a, b - with correct hash
	public static Vertex makeAbstraction(Vertex a, Vertex b) {
		Vertex v = new Vertex();
		v.operation = 'L';
		v.left = a;
		v.right = b;
		v.countHashAndCo(false);
		return v;
	}

	public static Vertex copyVertex(Vertex v) {
		if (v == null) {
			return null;
		}
		return new Vertex(v);
	}

	public void countHashAndCo(boolean goRecursive) {
		if (goRecursive && left != null) {
			left.countHashAndCo(true);
		}
		if (goRecursive && right != null) {
			right.countHashAndCo(true);
		}

		switch (operation) {
			case 'N':
				hash = 0;
				deep = 0;
				return;
			case 'L':
			case 'A':
				if (left == null || right == null) {
					throw new NullPointerException();
				}
				deep = left.deep + right.deep + 1;
				hash = left.hash * Initials.getHashLong(right.deep + 1) + operation * Initials.getHashLong(right.deep) + right.hash;    // ou dangerous =)
				return;

			case 'V':
				deep = propose.length();
				hash = propose.hashCode();
				return;

			case 'F':
				return;
			default:
				break;
		}
	}

	public boolean isRedex() {
		return (operation == 'A' && left.operation == 'L');
	}

	public String toStringOrNumeral() {
		Integer x = Numeral.getInteger(this);
		if (x != null) {
			return ("" + x);
		}
		return toString();
	}

	@Override
	public String toString() {
		switch (operation) {
			case 'N':
				return "vertex with null fields";

			case 'L':
				if (right.operation == 'V') {
					return '\\' + left.propose + '.' + right.propose;
				}
				return '\\' + left.propose + ".(" + right.toString() + ")";

			case 'A':
				StringBuilder s = new StringBuilder();
				if (left.operation != 'V') {
					s.append('(');
					s.append(left.toString());
					s.append(')');
				} else {
					s.append(left.propose);
				}
				s.append(' ');
				if (right.operation != 'V') {
					s.append('(');
					s.append(right.toString());
					s.append(')');
				} else {
					s.append(right.propose);
				}
				return s.toString();

			case 'V':
				return propose;

			case 'F':
				return "func";
			default:
				break;
		}
		return null;
	}

	@Deprecated
	@Override
	public int hashCode() {
		return (int) (hash);
	}

	public boolean equals(Vertex v) {
		return (v != null && this.hash == v.hash && (operation == v.operation && Equality.equalOrNulls(this.args, v.args) &&
				Equality.equalOrNulls(this.left, v.left) && Equality.equalOrNulls(this.right, v.right)));
	}

	@Override
	public boolean equals(Object v) {
		if (v == null) {
			return false;
		}
		if (v.getClass() == Vertex.class) {
			return equals((Vertex) v);
		}
		return (this == v);
	}

	public boolean equalsWithRenaming(Vertex v) {
		NamesDecorator namesDecorator = new NamesDecorator(this, v);
		return namesDecorator.nice.equals(namesDecorator.nice2);
	}

}
