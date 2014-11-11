package dima.utils;

import dima.utils.exceptions.IncorrectLambdaExpressionException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created  by dima  on 10.09.14.
 */
public class StringUtilities {


    public static Collection<Character> ALLOWEDSYMBOLS = new ArrayList<>();

    private static String unifyWhitespaces(String raw) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            if (Character.isWhitespace(raw.charAt(i)))        //TODO check if it is comprehensive
            {
                stringBuilder.append(' ');
            } else {
                stringBuilder.append(raw.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

    private static String killExcessWhitespaces(String raw) {
        //System.out.println("_:" + raw + ":");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            if (raw.charAt(i) != ' ' || (i != 0 && stringBuilder.charAt(stringBuilder.length() - 1) != '\\' &&
                    stringBuilder.charAt(stringBuilder.length() - 1) != '.' &&
                    stringBuilder.charAt(stringBuilder.length() - 1) != ' '))   //TODO is it all situations???
            {
                stringBuilder.append(raw.charAt(i));
            }
        }
        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public static String totallyTreatWhitespaces(String raw) {
        return killExcessWhitespaces(unifyWhitespaces(raw));   //TODO-want it is nice to me, but obviously slow
    }

    public static void checkCorrectnessLambdaExpression(String raw) throws IncorrectLambdaExpressionException {
        for (int i = 0; i < raw.length(); i++) {
            {
                if (!ALLOWEDSYMBOLS.contains(raw.charAt(i))) {
                    throw new IncorrectLambdaExpressionException("Incorrect symbol '" + raw.charAt(i)
                            + "' (int : " + (int) (raw.charAt(i)) + ") in \"" + raw + "\".");
                }
                if (i != 0 && raw.charAt(i - 1) == '\\' && raw.charAt(i) == '\\') {
                    throw new IncorrectLambdaExpressionException("Two \\ symbols together in \"" + raw + "\".");
                }
            }
        }

    }

    public static String killOuterBrackets(String raw, Brackets brackets) {
        int k = 0;
        for (int i = 0; i < raw.length(); i++) {
            if (raw.charAt(i) == '(' && brackets.closeIt(i) == raw.length() - i - 1) {
                k++;
            } else {
                break;
            }
        }
        return raw.substring(k, raw.length() - k);
    }

    public static String getName(String raw) throws IncorrectLambdaExpressionException {
        int k = 0;
        for (int i = 0; i < raw.length(); i++) {
            if (Character.isLetterOrDigit(raw.charAt(i)) || raw.charAt(i) == '\'') {
                k++;
            } else {
                break;
            }
        }
        if (k == 0) {
            throw new IncorrectLambdaExpressionException("It is thought to be a propose in \"" + raw + "\" from " + 0 + " position (0-indexed).");
        }
        return raw.substring(0, k);
    }

    public static String getNameBackwards(String raw, int endInclusive) throws IncorrectLambdaExpressionException {
        int k = 0;
        for (int i = endInclusive; i >= 0; i--) {
            if (Character.isLetterOrDigit(raw.charAt(i)) || raw.charAt(i) == '\'') {
                k++;
            } else {
                break;
            }
        }
        if (k == 0) {
            throw new IncorrectLambdaExpressionException("It is thought to be a propose in \"" + raw + "\" up to " + endInclusive + " position (0-indexed).");
        }
        return raw.substring(endInclusive - k + 1, endInclusive + 1);
    }

}









