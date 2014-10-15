package com.utils;

/**
 * Created by dima on 11.09.14.
 */
public class Equality {

	public static boolean equalOrNulls(Object x, Object y) {
		if (x == null && y == null) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		return x.equals(y);
	}
}
