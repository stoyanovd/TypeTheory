package com.testers;

import com.patterns.Numeral;
import com.supports.Initials;
import com.utils.exceptions.IncorrectLambdaExpressionException;
import com.utils.exceptions.WrongBracketsException;
import com.vertexes.Normalizer;
import com.vertexes.Parser;
import com.vertexes.Vertex;

/**
 * Created  by dima  on 22.10.14.
 */
public class Test {

	public static void main(String arg[]) {

		try {
			Initials.init();

			//String raw = Numeral.LPower + Numeral.getNumeralLambda(5) + Numeral.getNumeralLambda(3);
			String raw = Numeral.LMinus + Numeral.getNumeralLambda(7) + Numeral.getNumeralLambda(4);
			//String raw = Numeral.LFact + Numeral.getNumeralLambda(0);

			Vertex v = Parser.parseString(raw);

			Normalizer normalizer = new Normalizer(v);

			System.out.println(normalizer.ans.toStringOrNumeral());

		} catch (WrongBracketsException | IncorrectLambdaExpressionException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}

}
