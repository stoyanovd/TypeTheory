package dima.vertexes;

/**
 * Created  by dima  on 16.09.14.
 */
public class Substitution {

    private String propose;
    private Vertex replacement;

    public Substitution(String propose, Vertex replacement) {
        this.propose = propose;
        this.replacement = new Vertex(replacement);  //  Wow
    }

    public static Vertex remakeVertex(String propose, Vertex replacement, Vertex touchablePlace) {

        Substitution substitution = new Substitution(propose, replacement);

        return substitution.makeVertex(touchablePlace);
        /*
        long time = System.currentTimeMillis();
           try {
               return substitution.makeVertex(touchablePlace);
           }
           finally {
               DebugInformer.redTime += System.currentTimeMillis() - time;
           }*/
    }

    private Vertex makeVertex(Vertex v) {
        switch (v.operation) {
            case 'L': {
                if (v.left.propose.equals(propose)) {
                    return v;
                }
                v.right = makeVertex(v.right);
                v.countHashAndCo();
                return v;
            }
            case 'A': {
                v.left = makeVertex(v.left);
                v.right = makeVertex(v.right);
                v.countHashAndCo();
                return v;
            }
            case 'V': {
                if (!v.propose.equals(propose)) {
                    return v;
                }
                return replacement;
            }
            default: {
                throw new NullPointerException("These cases must be done");
            }
        }
    }

}
