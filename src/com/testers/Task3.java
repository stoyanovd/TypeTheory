package com.testers;

import com.supports.Initials;
import com.utils.MyPrinter;
import com.utils.MyReader;
import com.utils.exceptions.IncorrectLambdaExpressionException;
import com.utils.exceptions.WrongBracketsException;
import com.vertexes.Parser;
import com.vertexes.Substitution;
import com.vertexes.Vertex;

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

			if (!substitution.makeSubstitution(v))

			{
				myPrinter.printlnString("Нет свободы для подстановки для переменной " + substitution.changedVertex.propose + ".");
			} else {
				myPrinter.printLambdaExpression(substitution.changedVertex);
			}


		} catch (WrongBracketsException | IncorrectLambdaExpressionException | IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}
}
