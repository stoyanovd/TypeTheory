package com.vertexes;

import com.vertexes.exceptions.BoundProposeException;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by dima on 16.09.14.
 */
public class Substitution {

	private String propose;
	private Vertex replacement;

	private ArrayList<String> openedLambdas;
	private Set<String> replacementFreeProposes;

	public Vertex changedVertex;

	public Substitution(String propose, Vertex replacement) {
		this.propose = propose;
		this.replacement = replacement;
		openedLambdas = new ArrayList<>();
		changedVertex = null;
		replacementFreeProposes = Vertex.getFreeProposes(replacement);
	}

	public boolean makeSubstitution(Vertex v) {                        //CARE		not ideologically true using of Exceptions
		try {
			changedVertex = makeVertex(v);
		} catch (BoundProposeException e) {
			changedVertex = new Vertex();
			changedVertex.propose = e.getMessage();
			return false;
		}
		return true;
	}

	private Vertex makeVertex(Vertex v) throws BoundProposeException {
		switch (v.operation) {
			case 'L': {
				if (v.left.propose.equals(propose)) {
					return new Vertex(v);
				}
				openedLambdas.add(v.left.propose);
				Vertex x = new Vertex(v);
				x.right = makeVertex(v.right);            //CARE    mutable vertexes
				x.countHashAndCo();
				openedLambdas.remove(openedLambdas.size() - 1);
				return x;
			}
			case 'A': {
				Vertex x = new Vertex(v);
				x.left = makeVertex(v.left);
				x.right = makeVertex(v.right);
				x.countHashAndCo();
				return x;
			}
			case 'V': {
				if (!v.propose.equals(propose)) {
					return new Vertex(v);
				}
				for (String s : openedLambdas) {
					if (replacementFreeProposes.contains(s)) {
						throw new BoundProposeException(s);
					}
				}
				return new Vertex(replacement);
			}
			default: {
				throw new NullPointerException("These cases must be done");
			}
		}
	}

}
