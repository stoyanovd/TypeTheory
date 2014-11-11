package dima.utils;

/**
 * Created  by dima  on 11.09.14.
 */
public class Equality {

    public static boolean equalOrNulls(Object x, Object y) {
        return x == null && y == null || !(x == null || y == null) && x.equals(y);
    }
}
