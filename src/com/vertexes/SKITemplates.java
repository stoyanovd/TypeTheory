package com.vertexes;

import com.utils.exceptions.IncorrectLambdaExpressionException;
import com.utils.exceptions.WrongBracketsException;

/**
 * Created by dima on 15.10.14.
 */
public final class SKITemplates {

	private static Vertex K;
	private static Vertex S;
	private static Vertex I;

	public static Vertex getK() {
		if (K == null) {
			try {
				K = Parser.parseString("K");
			} catch (WrongBracketsException | IncorrectLambdaExpressionException e) {
				e.printStackTrace();
				System.out.println("Error in init K");
				throw new NullPointerException();
			}
		}
		return new Vertex(K);
	}

	public static Vertex getS() {
		if (S == null) {
			try {
				S = Parser.parseString("S");
			} catch (WrongBracketsException | IncorrectLambdaExpressionException e) {
				e.printStackTrace();
				System.out.println("Error in init S");
				throw new NullPointerException();
			}
		}
		return new Vertex(S);
	}

	public static Vertex getI() {
		if (I == null) {
			try {
				I = Parser.parseString("I");
			} catch (WrongBracketsException | IncorrectLambdaExpressionException e) {
				e.printStackTrace();
				System.out.println("Error in init I");
				throw new NullPointerException();
			}
		}
		return new Vertex(I);
	}
}
