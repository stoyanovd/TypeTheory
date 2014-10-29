package dima.vertexes;

import dima.supports.DebugInformer;

/**
 * Created  by dima on 01.10.14.
 */

public class Normalizer {

	public Vertex ans;

	public Normalizer(Vertex v) {

		ans = new Vertex(v);
		ans = tryGetNorm(ans);
		//ans = tryGetNormRecursive(ans);

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
			MyCache.dfsMap.put(new Vertex(t), 0);
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
		MyCache.dfsMap.put(new Vertex(t), a);

		return a;
	}

	private static Vertex reductRedex(Vertex v) {
		DebugInformer.reductCounts++;
		Vertex redex = new NiceVertex(v);
		if (MyCache.redexMap.containsKey(redex)) {
			DebugInformer.reductCountsMap++;
			return MyCache.redexMap.get(redex);
		}
		//-------------------------
		NamesDecorator namesDecorator = new NamesDecorator(redex.left, redex.right);
		Vertex leftNice = namesDecorator.getNewNice();
		Vertex rightNice = namesDecorator.getNewNice2();
		//-------------------------
		Vertex ans = Substitution.getNewSubstitutionVertex(leftNice.left.propose, rightNice, leftNice.right);
		/*Substitution substitution = new Substitution(leftNice.left.propose, rightNice);
		if (!substitution.makeSubstitution(leftNice.right)) {
			System.out.println("This not nice error with same names.\n" + substitution.getNewSubstituted().propose);
			throw new NullPointerException();
		}*/
		MyCache.redexMap.put(redex, new NiceVertex(ans));
		return ans;
	}

	private static Vertex tryGetNorm(Vertex input) {

		Vertex v = input;
		int curDfs;

		while (true) {
			v = new NiceVertex(v);
			curDfs = dfsFindRedexes(v);
			if (curDfs < 0) {
				return v;
			}
			//System.out.println("Step : " + v);
			//System.out.println("curDfs = " + curDfs);
			DebugInformer.gettingNormBigStep++;
			if (DebugInformer.gettingNormBigStep % 100 == 0) {
				DebugInformer.printStatistics();
				System.gc();
			}
			if (curDfs == 0) {
				v = reductRedex(v);
				continue;
			}
			Vertex t = v;
			for (; ; ) {
				//System.out.println("T: " + t);
				//System.out.println("l : " + dfsFindRedexes(t.left) + "  r : " + dfsFindRedexes(t.right) + "   t : " + dfsFindRedexes(t));
				int x = dfsFindRedexes(t.left);
				if (x == 0) {
					t.left = reductRedex(t.left);
					break;
				}
				if (x > 0) {
					t = t.left;
					continue;
				}
				x = dfsFindRedexes(t.right);
				if (x == 0) {
					t.right = reductRedex(t.right);
					break;
				}
				t = t.right;
			}
		}
	}

	private static void bigStep() {
		DebugInformer.gettingNormBigStep++;
		if (DebugInformer.gettingNormBigStep % 100 == 0) {
			DebugInformer.printStatistics();
			System.gc();
		}
	}

	private static Vertex tryGetNormRecursive(Vertex input) {
		Vertex v = new NiceVertex(input);
		while (dfsFindRedexes(v) >= 0) {
			bigStep();
			v = nextStepNewVertex(v);
		}
		return v;
	}

	private static Vertex nextStepNewVertex(Vertex v) {
		DebugInformer.stepCounts++;
		if (MyCache.stepMap.containsKey(v)) {
			DebugInformer.stepCountsMap++;
			return new Vertex(MyCache.stepMap.get(v));
		}
		if (dfsFindRedexes(v) == 0) {
			Vertex t = reductRedex(v);
			MyCache.stepMap.put(new Vertex(v), new Vertex(t));
			return t;
		}
		Vertex t = new Vertex(v);
		if (dfsFindRedexes(v.left) >= 0) {
			t.left = new Vertex(nextStepNewVertex(v.left));
		} else {
			t.right = new Vertex(nextStepNewVertex(v.right));
		}
		t.countHashAndCo(false);
		MyCache.stepMap.put(new Vertex(v), new Vertex(t));
		return t;
	}
}
