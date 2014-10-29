package dima.supports;

import dima.Configuration;
import dima.vertexes.MyCache;

/**
 * Created  by dima  on 26.10.14.
 */
public class DebugInformer {


	public static int reductCounts = 0;
	public static int reductCountsMap = 0;
	public static int stepCounts = 0;
	public static int stepCountsMap = 0;
	public static int dfsCounts = 0;
	public static int dfsCountsMap = 0;
	public static int namesDecoratorCounts = 0;
	public static int namesDecoratorCountsMap = 0;
	public static int namesDecoratorPairsCounts = 0;
	public static int namesDecoratorPairsCountsMap = 0;

	public static int gettingNormBigStep = 0;

	public static int maxLambdaLength = 0;

	private static double getQuotient(int x, int y) {
		return (1.0 * x) / y;
	}

	public static void printStatistics() {
		System.out.println("STEP : " + gettingNormBigStep);
		System.out.println("stepCounts : " + stepCounts + "     quotient : " + getQuotient(stepCountsMap, stepCounts));
		System.out.println("reductCounts : " + reductCounts + "     quotient : " + getQuotient(reductCountsMap, reductCounts));
		System.out.println("dfsCounts : " + dfsCounts + "     quotient : " + getQuotient(dfsCountsMap, dfsCounts));
		System.out.println("namesDecoratorCounts : " + namesDecoratorCounts + "     quotient : " + getQuotient(namesDecoratorCountsMap, namesDecoratorCounts));
		System.out.println("namesDecoratorPairsCounts : " + namesDecoratorPairsCounts + "     quotient : "
				+ getQuotient(namesDecoratorPairsCountsMap, namesDecoratorPairsCounts));

		System.out.println("Maps sizes :");
		System.out.println("dfs : " + MyCache.dfsMap.size());
		System.out.println("dsu : " + MyCache.dsuMap.size());
		System.out.println("singles : " + MyCache.niceMap.size());
		System.out.println("pairs : " + MyCache.nicePairsMap.size());
		System.out.println("max length : " + maxLambdaLength);
		System.out.println("______________");
	}

	public static void printStatisticsIfNorm() {
		if (Configuration.DEBUG_MODE) {
			if (stepCounts % 5000 == 0) {
				printStatistics();
			}
		}
	}
}
