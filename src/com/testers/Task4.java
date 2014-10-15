package com.testers;

import com.supports.Initials;
import com.utils.MyPrinter;
import com.utils.MyReader;
import com.utils.exceptions.IncorrectLambdaExpressionException;
import com.utils.exceptions.WrongBracketsException;
import com.vertexes.Normalizer;
import com.vertexes.Parser;
import com.vertexes.Vertex;

import java.io.IOException;

/**
 * Created by dima on 01.10.14.
 */
public class Task4 {

	public static void main(String arg[]) {

		try {
			Initials.init();
			MyReader myReader = new MyReader("tests/task4.in");
			MyPrinter myPrinter = new MyPrinter("tests/task4.out");

			String raw = myReader.readString();
			Vertex v = Parser.parseString(raw);

			Normalizer normalizer = new Normalizer(v);
			Vertex t = normalizer.ans;

			myPrinter.printLambdaExpression(t);

		} catch (WrongBracketsException | IncorrectLambdaExpressionException | IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}
}
