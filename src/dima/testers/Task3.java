package dima.testers;

import dima.supports.Initials;
import dima.utils.MyPrinter;
import dima.utils.MyReader;
import dima.utils.exceptions.IncorrectLambdaExpressionException;
import dima.utils.exceptions.WrongBracketsException;
import dima.vertexes.Parser;
import dima.vertexes.Substitution;
import dima.vertexes.Vertex;

import java.io.IOException;

/**
 * Created by dima on 16.09.14.
 */

public class Task3 {

	public static void main(String arg[]) {

		try {
			Initials.init();
			MyReader myReader = new MyReader("tests/task3.in");
			MyPrinter myPrinter = new MyPrinter("tests/task3.out");

			String raw = myReader.readString();

			if (!raw.contains("[")) {
				myPrinter.printlnString("There isn't [ in input.\nFormat is    expr[x:=expr]");
				return;
			}

			Vertex v = Parser.parseString(raw.substring(0, raw.indexOf('[')));
			Substitution substitution = new Substitution(raw.substring(raw.indexOf('[') + 1, raw.indexOf(":=")),
					Parser.parseString(raw.substring(raw.indexOf(":=") + 2, raw.indexOf(']'))));

			if (!substitution.makeSubstitution(v)) {
				myPrinter.printlnString("Нет свободы для подстановки для переменной " + substitution.getNewSubstituted().propose + ".");
			} else {
				myPrinter.printLambdaExpression(substitution.getNewSubstituted());
			}


		} catch (WrongBracketsException | IncorrectLambdaExpressionException | IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}
}
