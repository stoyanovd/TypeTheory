package dima.testers;

import dima.patterns.Numeral;
import dima.supports.DebugInformer;
import dima.supports.Initials;
import dima.utils.MyPrinter;
import dima.utils.exceptions.IncorrectLambdaExpressionException;
import dima.utils.exceptions.WrongBracketsException;
import dima.vertexes.Normalizer;
import dima.vertexes.Parser;
import dima.vertexes.Vertex;

/**
 * Created  by dima  on 22.10.14.
 */
public class Test {

	public static void main(String arg[]) {

		try {
			Initials.init();
			long timeStart = System.currentTimeMillis();
			//String raw = Numeral.LMinus + Numeral.getNumeralLambda(8) + Numeral.getNumeralLambda(6);
			//String raw = Numeral.LMinus + Numeral.getNumeralLambda(7) + Numeral.getNumeralLambda(4);
			String raw = Numeral.LFact + Numeral.getNumeralLambda(0);

			Vertex v = Parser.parseString(raw);

			System.out.println("Read : ");
			System.out.println(v);

			Normalizer normalizer = new Normalizer(v);

			System.out.println("ANSWER : " + normalizer.ans.toStringOrNumeral());
			MyPrinter.printTime(timeStart);
			DebugInformer.printStatistics();

		} catch (WrongBracketsException | IncorrectLambdaExpressionException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}

}
