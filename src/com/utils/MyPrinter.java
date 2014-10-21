package com.utils; /**
 * Created by dima on 11.09.14.
 */

import com.Configuration;
import com.utils.exceptions.WrongBracketsException;
import com.vertexes.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MyPrinter {

	String outputFile;
	PrintWriter out;

	public MyPrinter(String _outputFile) {
		outputFile = _outputFile;
	}

	public void printLambdaExpression(Vertex v) throws FileNotFoundException {
		try {
			out = new PrintWriter(new File(outputFile));
			if (Configuration.DuplicateOutputToConsole) {
				System.out.println("In output file was printed:");
				System.out.println(v.toString());
			}
			out.println(v.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	public void printlnString(String s) throws FileNotFoundException {
		try {
			out = new PrintWriter(new File(outputFile));
			if (Configuration.DuplicateOutputToConsole) {
				System.out.print("In output file was printed    :");
				System.out.println(s);
			}
			out.println(s);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static void printTime(long time) {
		System.out.println("Time used: " + (System.currentTimeMillis() - time));
	}


	public static void printLambdaExpressionAsTree(Vertex v) throws FileNotFoundException, WrongBracketsException {

		String s = v.toString();
		Brackets brackets = new Brackets(s);

		for (int k = 0; k <= brackets.maxDeep; k++) {
			int b = 0;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == '(') {
					b++;
				}
				if (b == k) {
					System.out.print(s.charAt(i));
				} else {
					System.out.print(' ');
				}
				if (s.charAt(i) == ')') {
					b--;
				}
			}
			System.out.println();
		}
	}


}
