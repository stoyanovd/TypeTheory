package com.vertexes;

import com.supports.Initials;
import com.utils.Equality;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dima on 09.09.14.
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
			this.args = new ArrayList<Vertex>(v.args);
		}
		countHashAndCo();
	}

	public static Set<String> getFreeProposes(Vertex v) {

		switch (v.operation) {
			case 'N':
				return new HashSet<>();                //  or null,  maybe????

			case 'L': {
				Set<String> x = new HashSet<>(getFreeProposes(v.right));
				x.remove(v.left.propose);
				return x;
			}
			case 'A': {
				Set<String> x = new HashSet<>(getFreeProposes(v.left));
				x.addAll(getFreeProposes(v.right));
				return x;
			}

			case 'V': {
				Set<String> x = new HashSet<>();
				x.add(v.propose);
				return x;
			}
			case 'F':
				return null;            //TODO-func
		}
		return null;
	}

	public void countHashAndCo() {
		if (left != null) {
			left.countHashAndCo();
		}
		if (right != null) {
			right.countHashAndCo();
		}

		switch (operation) {
			case 'N':
				hash = 0;
				deep = 0;
				return;
			case 'L':
			case 'A':
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

	public static Vertex copyVertex(Vertex v) {
		if (v == null) {
			return null;
		}
		return new Vertex(v);
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
				return new String(propose);

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

}
