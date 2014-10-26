package dima.testers;

import dima.supports.Initials;
import dima.utils.MyPrinter;
import dima.utils.MyReader;
import dima.utils.exceptions.IncorrectLambdaExpressionException;
import dima.utils.exceptions.WrongBracketsException;
import dima.vertexes.Parser;
import dima.vertexes.SKITransformer;
import dima.vertexes.Vertex;

import java.io.IOException;

/**
 * Created by dima on 15.10.14.
 */
public class Task5 {

	public static void main(String arg[]) {

		try {
			Initials.init();
			MyReader myReader = new MyReader("tests/task5.in");
			MyPrinter myPrinter = new MyPrinter("tests/task5.out");

			String raw = myReader.readString();

			Vertex v = Parser.parseString(raw);
			Vertex t = SKITransformer.getSKI(v);
			myPrinter.printLambdaExpression(t);

			checkDiff(t);

		} catch (WrongBracketsException | IncorrectLambdaExpressionException | IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}

	static void checkDiff(Vertex t) throws IOException, WrongBracketsException, IncorrectLambdaExpressionException {
		MyReader myReader = new MyReader("tests/answer.out");

		String raw = myReader.readString();

		Vertex v = Parser.parseString(raw);

		System.out.println("Compare to answer.out : " + v.equals(t));
	}
}
