package dima.vertexes;

/**
 * Created  by dima  on 15.10.14.
 */
public class SKITransformer {

    public static Vertex getSKI(Vertex v) {
        if (v == null || v.operation == 'N') {
            return null;
        }
        switch (v.operation) {
            case 'V':
                return new Vertex(v);                                //  1
            case 'A':
                return Vertex.makeApplication(getSKI(v.left), getSKI(v.right));            //  3
            case 'L': {
                if (!Vertex.getFreeProposes(v.right).contains(v.left.propose))                    //	2
                {
                    return Vertex.makeApplication(SKITemplates.getK(), getSKI(v.right));
                }

                if (v.left.equals(v.right)) {
                    return SKITemplates.getI();                        //	4
                }

                if (v.right.operation == 'L') {
                    return getSKI(Vertex.makeAbstraction(v.left, getSKI(v.right)));        //	5
                }

                Vertex vl = Vertex.makeApplication(SKITemplates.getS(), getSKI(Vertex.makeAbstraction(v.left, v.right.left)));
                Vertex vr = getSKI(Vertex.makeAbstraction(v.left, v.right.right));
                return Vertex.makeApplication(vl, vr);                                    //6
            }
        }
        return null;
    }
}
