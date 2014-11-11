package dima.testers;

import dima.patterns.Numeral;
import dima.supports.Initials;
import dima.utils.MyPrinter;
import dima.utils.MyReader;
import dima.utils.exceptions.IncorrectLambdaExpressionException;
import dima.utils.exceptions.WrongBracketsException;
import dima.vertexes.Normalizer;
import dima.vertexes.Parser;
import dima.vertexes.Vertex;

import java.io.IOException;

/**
 * Created  by dima  on 01.10.14.
 */
public class Task4 {

    public static void main(String arg[]) {

        try {
            Initials.init();
            MyReader myReader = new MyReader("tests/task4.in");
            MyPrinter myPrinter = new MyPrinter("tests/task4.out");

            String raw = myReader.readString();
            Vertex v = Parser.parseString(raw);

            System.out.println("after parse get this:");
            System.out.println(v.toString());

            MyPrinter.printLambdaExpressionAsTree(v);
            Normalizer normalizer = new Normalizer(v);
            Vertex t = normalizer.ans;

            Integer x = Numeral.getInteger(t);
            if (x != null) {
                myPrinter.printlnString("" + x);
            } else {
                myPrinter.printLambdaExpression(t);
            }

        } catch (WrongBracketsException | IncorrectLambdaExpressionException | IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

    }
}
