package dima.utils;

/**
 * Created  by dima  on 10.09.14.
 */

import dima.utils.exceptions.WrongBracketsException;

import java.util.ArrayList;

public class Brackets {

	private int getClose[];
	private int getOpen[];

	public int maxDeep;

	public int closeIt(int i) {
		return getClose[i];
	}

	public int openIt(int i) {
		return getOpen[i];
	}

	public Brackets(String s) throws WrongBracketsException {
		getClose = new int[s.length()];
		getOpen = new int[s.length()];

		int k = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				k++;
				maxDeep = Math.max(k, maxDeep);
			} else if (s.charAt(i) == ')') {
				k--;
			}
			if (k < 0) {
				throw new WrongBracketsException("In \"" + s + "\" at " + i + " position (0-indexed) is wrong brackets balance.\n" +
						"Brackets: " + '(' + " and " + ')' + ".\n");
			}
		}
		if (k != 0) {
			throw new WrongBracketsException("In \"" + s + "\" is wrong total brackets balance. Brackets: \" + openBracket +" +
					" and " + ')' + ".\n");
		}

		ArrayList<Integer> openBraces = new ArrayList<>();

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				openBraces.add(i);
			} else if (s.charAt(i) == ')') {
				getOpen[i] = openBraces.get(openBraces.size() - 1);
				getClose[openBraces.get(openBraces.size() - 1)] = i;
				openBraces.remove(openBraces.size() - 1);
			}
		}
	}
}