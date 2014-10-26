package dima.supports;

import dima.utils.StringUtilities;

import java.util.Collection;
import java.util.Random;

/**
 * Created by dima  on 11.09.14.
 */
public class Initials {

	public static final Random random = new Random();
	public static int HASHPRIMESSIZE = 20000;
	public static long HASHPRIMES[] = new long[HASHPRIMESSIZE];
	public static long HASHPRIME = 17239;
	public static Collection<Character> ALLOWEDSYMBOLS = StringUtilities.ALLOWEDSYMBOLS;

	public static void init() {
		HASHPRIMES[0] = 1;
		for (int i = 1; i < HASHPRIMESSIZE; i++) {
			HASHPRIMES[i] = HASHPRIMES[i - 1] * HASHPRIME;
		}

		for (char x = 'a'; x <= 'z'; x++) {
			ALLOWEDSYMBOLS.add(x);
		}
		for (char x = 'A'; x <= 'Z'; x++) {
			ALLOWEDSYMBOLS.add(x);
		}
		for (char x = '0'; x <= '9'; x++) {
			ALLOWEDSYMBOLS.add(x);
		}
		ALLOWEDSYMBOLS.add('(');
		ALLOWEDSYMBOLS.add(')');
		ALLOWEDSYMBOLS.add('\'');
		ALLOWEDSYMBOLS.add('\\');
		ALLOWEDSYMBOLS.add(' ');
		ALLOWEDSYMBOLS.add('.');
		ALLOWEDSYMBOLS.add('[');
		ALLOWEDSYMBOLS.add(']');
		ALLOWEDSYMBOLS.add(':');
		ALLOWEDSYMBOLS.add('=');
	}

	public static long getHashLong(int i) {
		return HASHPRIMES[i % HASHPRIMESSIZE];
	}

	public static boolean getNextBoolean() {
		return random.nextBoolean();
	}


}