package dima.vertexes;

import dima.patterns.Numeral;
import dima.utils.Equality;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  by dima on 09.09.14.
 */
public class Vertex {

    public static final int MAX_DFS_DEEP = 100000007;
    public char operation;    /*	L : for lambda
                            V : for variable
       						A : for application
       						F : for functions
       						N : for null
      					*/
    public String propose;
    public Vertex left;
    public Vertex right;
    public int dfs;
    private long hash;
    private int deep;

    public Vertex() {
        operation = 'N';
        propose = null;
        left = null;
        right = null;
        hash = 0;
        deep = 0;
        dfs = MAX_DFS_DEEP;
    }

    public Vertex(Vertex v) {
        this.operation = v.operation;
        this.propose = v.propose;

        this.left = copyVertex(v.left);
        this.right = copyVertex(v.right);

        this.deep = v.deep;
        this.hash = v.hash;
        this.dfs = v.dfs;

    }

    public static List<String> getFreeProposes(Vertex v) {

        List<String> ans = new ArrayList<>();
        getFreeProposesInner(v, new ArrayList<>(), ans);
        return ans;
    }

    private static void getFreeProposesInner(Vertex v, List<String> bounded, List<String> ansNames) {

        switch (v.operation) {
            case 'N':
                throw new NullPointerException();                //  or null,  maybe????

            case 'L': {
                boolean was = bounded.contains(v.left.propose);
                if (!was) {
                    bounded.add(v.left.propose);
                }
                getFreeProposesInner(v.right, bounded, ansNames);
                if (!was) {
                    bounded.remove(v.left.propose);
                }
                return;
            }
            case 'A': {
                getFreeProposesInner(v.left, bounded, ansNames);
                getFreeProposesInner(v.right, bounded, ansNames);
                return;
            }

            case 'V': {
                if (!bounded.contains(v.propose)) {
                    ansNames.add(v.propose);
                }
            }
        }
    }

    //  We supposed a, b - with correct hash
    public static Vertex makeApplication(Vertex a, Vertex b) {
        Vertex v = new Vertex();
        v.operation = 'A';
        v.left = a;
        v.right = b;
        v.countHashAndCo();
        return v;
    }

    //  We supposed a, b - with correct hash
    public static Vertex makeAbstraction(Vertex a, Vertex b) {
        Vertex v = new Vertex();
        v.operation = 'L';
        v.left = a;
        v.right = b;
        v.countHashAndCo();
        return v;
    }

    public static Vertex copyVertex(Vertex v) {
        if (v == null) {
            return null;
        }
        if (v.left == null) {
            return v;
        }
        return new Vertex(v);
    }

    public void countHashAndCo() {

        switch (operation) {
            case 'N':
                hash = 0;
                deep = 0;
                dfs = MAX_DFS_DEEP;
                return;
            case 'L':
            case 'A':
                hash = left.hash * 31 + operation * 17 + right.hash;
                if (isRedex()) {
                    dfs = 0;
                } else {
                    dfs = Math.min(left.dfs, right.dfs) + 1;
                }
                return;

            case 'V':
                hash = propose.hashCode();
                dfs = MAX_DFS_DEEP;
                return;

            case 'F':
                return;
            default:
                break;
        }
    }

    public boolean isRedex() {
        return (operation == 'A' && left.operation == 'L');
    }

    public String toStringOrNumeral() {
        Integer x = Numeral.getInteger(this);
        if (x != null) {
            return ("" + x);
        }
        return toString();
    }

    @Override
    public String toString() {
        switch (operation) {
            case 'N':
                return "vertex with null fields";

            case 'L':
                if (right.operation == 'V') {
                    return '\\' + left.propose + '.' + right.propose;
                }
                return '\\' + left.propose + ".(" + right.toString() + ")";

            case 'A':
                StringBuilder s = new StringBuilder();
                if (left.operation != 'V') {
                    s.append('(');
                    s.append(left.toString());
                    s.append(')');
                } else {
                    s.append(left.propose);
                }
                s.append(' ');
                if (right.operation != 'V') {
                    s.append('(');
                    s.append(right.toString());
                    s.append(')');
                } else {
                    s.append(right.propose);
                }
                return s.toString();

            case 'V':
                return propose;

            case 'F':
                return "func";
            default:
                break;
        }
        return null;
    }

    @Override
    public int hashCode() {
        return (int) (hash);
    }

    public boolean equals(Vertex v) {
        return this == v || (v != null && this.hash == v.hash &&
                (operation == v.operation &&
                        Equality.equalOrNulls(this.left, v.left) && Equality.equalOrNulls(this.right, v.right)));
    }

    @Override
    public boolean equals(Object v) {
        if (this == v) {
            return true;
        }
        if (v == null) {
            return false;
        }
        if (v instanceof Vertex) {
            return equals((Vertex) v);
        }
        return (this == v);
    }

}
