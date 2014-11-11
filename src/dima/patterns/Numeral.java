package dima.patterns;

import dima.vertexes.Vertex;

/**
 * Created  by dima  on 22.10.14.
 */
public class Numeral {

    public static final String NOmega = " ((\\x.x x) (\\x.x x)) ";

    public static final String LPlus = " (\\n.\\m.\\s.\\z.n s (m s z)) ";
    public static final String LMul = " (\\n.\\m.\\s.n (m s)) ";
    public static final String LPower = " (\\n.\\m.\\s.\\z.m n s z) ";
    public static final String LTrue = " (\\a.\\b.a) ";
    public static final String LFalse = " (\\a.\\b.b) ";
    public static final String LIf = " (\\p.\\t.\\e.p t e) ";
    public static final String LAnd = " (\\n.\\m." + LIf + " n m " + LFalse + ") ";
    public static final String LOr = " (\\n.\\m." + LIf + " n " + LTrue + " m) ";
    public static final String LNot = " (\\b." + LIf + " b " + LFalse + LTrue + ") ";
    public static final String LIsZero = " (\\n.n (\\c." + LFalse + ")" + LTrue + ") ";
    public static final String LPair = " (\\a.\\b.\\t.t a b) ";
    public static final String LFst = " (\\p.p" + LTrue + ") ";
    public static final String LSnd = " (\\p.p" + LFalse + ") ";
    public static final String LPred = " (\\n.\\s.\\z." + LSnd + "(n (\\p." + LPair + "(s (" + LFst + "p)) (" + LFst + "p)) (" + LPair + "z z))) ";
    public static final String LMinus = " (\\n.\\m.m" + LPred + "n) ";

    public static final String LFix = " (\\f.(\\x.f (x x)) (\\x.f (x x))) ";
    private static final String LFact_H = " (\\f.\\x." + LIf + "(" + LIsZero + "x)" + getNumeralLambda(1) + "(" + LMul +
            "x (f (" + LPred + "x)))) ";
    public static final String LFact = " (" + LFix + LFact_H + ") ";


    public static String getNumeralLambda(int x) {
        StringBuilder s = new StringBuilder(" (\\s.\\z.");
        for (int i = 0; i < x; i++) {
            s.append("s (");
        }
        s.append("z");
        for (int i = 0; i < x; i++) {
            s.append(")");
        }
        s.append(") ");
        return s.toString();
    }

    public static Integer getInteger(Vertex v) {
        if (v == null || v.operation != 'L' || v.right.operation != 'L') {
            return null;
        }
        String s = v.left.propose;
        String z = v.right.left.propose;

        int i = 0;
        Vertex t = v.right.right;
        while (true) {
            if (t == null) {
                return null;
            }
            if (t.propose != null && t.propose.equals(z)) {
                return i;
            }
            if (t.operation == 'A' && t.left.propose != null && t.left.propose.equals(s)) {
                i++;
                t = t.right;
                continue;
            }
            return null;
        }
    }
}
