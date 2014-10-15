package com.testers;

import com.supports.Initials;
import com.utils.MyPrinter;
import com.utils.MyReader;
import com.utils.exceptions.IncorrectLambdaExpressionException;
import com.utils.exceptions.WrongBracketsException;
import com.vertexes.Parser;
import com.vertexes.Vertex;

import java.io.IOException;
import java.util.TreeSet;

/**
 * Created by dima on 16.09.14.
 */

public class Task2 {

	public static void main(String arg[]) {

		try {
			Initials.init();
			MyReader myReader = new MyReader("tests/task2.in");
			MyPrinter myPrinter = new MyPrinter("tests/task2.out");

			String raw = myReader.readString();
			System.out.println("This was obtained after reading and initial simplify:\n\"" + raw + "\"\n");

			Vertex v = Parser.parseString(raw);
			myPrinter.printLambdaExpression(v);

			System.out.println("\n");
			TreeSet<String> proposes = new TreeSet<>(Vertex.getFreeProposes(v));
			for (String s : proposes) {
				myPrinter.printlnString(s);                //TODO  It is sorted order, isn't it?
			}
			MyPrinter.printLambdaExpressionAsTree(v);

		} catch (WrongBracketsException | IncorrectLambdaExpressionException | IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}
}
