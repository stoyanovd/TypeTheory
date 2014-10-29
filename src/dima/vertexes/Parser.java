package dima.vertexes;

import dima.utils.Brackets;
import dima.utils.StringUtilities;
import dima.utils.exceptions.IncorrectLambdaExpressionException;
import dima.utils.exceptions.WrongBracketsException;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created  by dima  on 16.09.14.
 */
public class Parser {

	@SuppressWarnings("New Vertex or null is returned. (not link to old)")
	public static Vertex getOneProposeVertex(String rawWithOnlyPropose) throws IncorrectLambdaExpressionException, WrongBracketsException {
		Vertex v = new Vertex();
		v.operation = 'V';
		v.propose = StringUtilities.getName(rawWithOnlyPropose);                            //TODO-speed
		if (v.propose.length() != rawWithOnlyPropose.length()) {
			throw new IncorrectLambdaExpressionException("\"" + rawWithOnlyPropose + "\" is thought to be a propose, but contains not only \"" + v.propose + "\"");
		}
		v.countHashAndCo(false);
		return v;
	}

	@SuppressWarnings("New Vertex or null is returned. (not link to old)")
	public static Vertex parseString(String _raw) throws WrongBracketsException, IncorrectLambdaExpressionException {
		if (_raw == null) {
			throw new NullPointerException("Null string want to be parsed.");
		}
		String raw = StringUtilities.totallyTreatWhitespaces(_raw);
		if ("".equals(raw)) {
			return null;
		}

		StringUtilities.checkCorrectnessLambdaExpression(raw);
		Brackets brackets = new Brackets(raw);
		raw = StringUtilities.killOuterBrackets(raw, brackets);
		brackets = new Brackets(raw);

		//System.out.println("parse:" + raw + ":");

		if (raw.indexOf('.') == -1 && raw.indexOf(' ') == -1 && raw.indexOf('(') == -1) {
			return getOneProposeVertex(raw);
		}

		Deque<Vertex> deque = new LinkedList<>();

		int k = raw.length();
		for (int i = raw.length() - 1; i >= 0; i--) {
			switch (raw.charAt(i)) {
				case '.': {
					deque.addFirst(parseString(raw.substring(i + 1, k)));
					//System.out.println("inner parse:" + raw + ":   i=" + i + ", \'" + raw.charAt(i) + "\'");
					Vertex v = mergeVertexes(deque);
					String x = StringUtilities.getNameBackwards(raw, i - 1);
					Vertex t = new Vertex();
					t.right = v;
					t.left = getOneProposeVertex(x);
					t.operation = 'L';
					t.countHashAndCo(false);
					deque.addFirst(t);
					i -= x.length() + 1;
					k = i;
					break;
				}
				case ' ': {
					if (i + 1 == k) {
						k--;
						break;
					}
					deque.addFirst(parseString(raw.substring(i + 1, k)));
					k = i;
					break;
				}
				case ')':
					i = brackets.openIt(i);
					break;
			}
		}
		if (k != 0) {
			deque.addFirst(parseString(raw.substring(0, k)));
		}

		return mergeVertexes(deque);            //TODO-func
	}

	private static Vertex mergeVertexes(Queue<Vertex> queue) {

		Vertex v = null;
		while (!queue.isEmpty()) {
			Vertex t = queue.poll();
			if (t == null) {
				continue;
			}
			if (v == null) {
				v = t;
				continue;
			}
			Vertex sum = new Vertex();
			sum.left = v;
			sum.right = t;
			sum.operation = 'A';
			sum.countHashAndCo(false);
			v = sum;                            //TODO examine this point
		}
		/*if (v != null) {
			System.out.println("finish merge:" + v.toString());
		}*/
		return v;
	}

}
