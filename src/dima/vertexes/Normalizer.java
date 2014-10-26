package dima.vertexes;

import dima.supports.DebugInformer;

import java.util.Stack;

/**
 * Created by dima on 01.10.14.
 */

public class Normalizer {

	public Vertex ans;

	public Normalizer(Vertex v) {

		ans = new Vertex(v);
		ans = getNorm(ans);

		System.out.println("ans:");
		System.out.println(ans.toString());

		NamesDecorator namesDecorator = new NamesDecorator(ans);
		ans = namesDecorator.getNewNice();

		System.out.println("nice:");
		System.out.println(ans.toString());
		System.out.println(dfsFindRedexes(ans));
	}

	public static boolean hasRedex(Vertex t) {
		return (dfsFindRedexes(t) != -1);
	}

	private static int dfsFindRedexes(Vertex t) {
		if (t == null) {
			return -1;
		}
		DebugInformer.dfsCounts++;
		if (MyCache.dfsMap.containsKey(t)) {
			DebugInformer.dfsCountsMap++;
			return MyCache.dfsMap.get(t);
		}
		if (t.isRedex()) {
			MyCache.dfsMap.put(t, 0);
			return 0;
		}
		int x = dfsFindRedexes(t.left);
		int y = dfsFindRedexes(t.right);

		int a;
		if (x == -1 && y == -1) {
			a = -1;
		} else if (x != -1 && y != -1) {
			a = Math.min(x, y) + 1;
		} else {
			a = (-x * y) + 1;
		}
		MyCache.dfsMap.put(t, a);
		return a;
	}

	private static Vertex reductRedex(Vertex redex) {
		DebugInformer.reductCounts++;
		Vertex newFullVertex = DSUEntry.getNewFullVertex(redex);
		if (newFullVertex != null) {
			DebugInformer.reductCountsMap++;
			return newFullVertex;
		}
		NamesDecorator namesDecorator = new NamesDecorator(redex.left, redex.right);
		Vertex leftNice = namesDecorator.getNewNice();
		Vertex rightNice = namesDecorator.getNewNice2();
		Substitution substitution = new Substitution(leftNice.left.propose, rightNice);
		if (!substitution.makeSubstitution(leftNice.right)) {
			System.out.println("This not nice error with same names.\n" + substitution.getNewSubstituted().propose);
			throw new NullPointerException();
		}
		return substitution.getNewSubstituted();
	}

	private static Vertex getNorm(Vertex v) {
		Stack<Vertex> stack = new Stack<>();
		stack.add(v);
		while (!stack.isEmpty()) {
			Vertex t = stack.pop();
			produceNormInQueue(t, stack);
		}
		return DSUEntry.getNewFullVertex(v);
	}

	private static void produceNormInQueue(Vertex v, Stack<Vertex> stack) {
		DebugInformer.normCounts++;
		DebugInformer.printStatisticsIfNorm();

		DSUEntry.tryInitDSUEntry(v);

		if (DSUEntry.hasFullVertex(v)) {
			DebugInformer.normCountsMap++;
			return;
		}

		//DebugInformer.maxLambdaLength = Math.max(DebugInformer.maxLambdaLength, v.toString().length());

		if (!hasRedex(v)) {
			DSUEntry.setFull(v);
			return;
		}

		if (dfsFindRedexes(v) == 0) {
			Vertex t = reductRedex(v);
			DSUEntry.tryInitDSUEntry(t);
			DSUEntry.union(v, t);
			stack.push(v);
			stack.push(t);
			return;
		}

		Vertex t = new Vertex(v);
		int x = dfsFindRedexes(v.left);
		int y = dfsFindRedexes(v.right);
		boolean leftBetter = (x != -1 && (y == -1 || x <= y));
		DSUEntry.tryInitDSUEntry(leftBetter ? t.left : t.right);
		Vertex newFullVertex = DSUEntry.getNewFullVertex(leftBetter ? t.left : t.right);
		if (newFullVertex == null) {
			stack.push(v);
			stack.push(leftBetter ? t.left : t.right);
			if (stack.peek().equals(v)) {
				throw new NullPointerException("OOOOh infinite cycle!");            //TODO  reformat this
			}
			return;
		}
		if (leftBetter) {
			t.left = newFullVertex;
		} else {
			t.right = newFullVertex;
		}
		t.countHashAndCo(false);
		DSUEntry.tryInitDSUEntry(t);
		DSUEntry.union(v, t);
		stack.push(v);
		stack.push(t);
	}
}
