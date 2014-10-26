package dima.testers;

import dima.supports.Initials;
import dima.utils.MyPrinter;
import dima.utils.MyReader;
import dima.utils.exceptions.IncorrectLambdaExpressionException;
import dima.utils.exceptions.WrongBracketsException;
import dima.vertexes.Parser;
import dima.vertexes.Vertex;

import java.io.IOException;

/**
 * Created by dima on 10.09.14.
 */
public class Task1 {

	public static void main(String arg[]) {

		try {
			Initials.init();
			MyReader myReader = new MyReader("tests/task1.in");
			MyPrinter myPrinter = new MyPrinter("tests/task1.out");

			String raw = myReader.readString();
			System.out.println("This was obtained after reading and initial simplify:\n\"" + raw + "\"\n");


			Vertex v = Parser.parseString(raw);
			myPrinter.printLambdaExpression(v);
			System.out.println();
			MyPrinter.printLambdaExpressionAsTree(v);

		} catch (WrongBracketsException | IncorrectLambdaExpressionException | IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}
}
