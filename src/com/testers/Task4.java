package com.testers;

import com.supports.Initials;
import com.utils.MyPrinter;
import com.utils.MyReader;

import java.io.IOException;

/**
 * Created by dima on 01.10.14.
 */
public class Task4 {public static void main(String arg[]) {

	try {
		Initials.init();
		MyReader myReader = new MyReader("tests/task4.in");
		MyPrinter myPrinter = new MyPrinter("tests/task4.out");

		String raw = myReader.readString();



	} catch (/*WrongBracketsException | IncorrectLambdaExpressionException | */IOException e) {
		System.out.println(e.toString());
		e.printStackTrace();
	}

}

}
