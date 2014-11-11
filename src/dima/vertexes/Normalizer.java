package dima.vertexes;

import dima.supports.DebugInformer;

/**
 * Created  by dima on 01.10.14.
 */

public class Normalizer {

    public Vertex ans;

    public Normalizer(Vertex v) {

        ans = new Vertex(v);
        NamesDecorator.makeNiceRemakeVertex(ans);
        //ans = tryGetNorm(ans);
        ans = tryGetNormRecursive(ans);

        System.out.println("ans:");
        System.out.println(ans.toString());

        NamesDecorator.makeNiceRemakeVertex(ans);

        System.out.println("nice:");
        System.out.println(ans.toString());
    }

    private static void reductRedexRemakeVertex(VertexContainer vertexContainer) {
        DebugInformer.reductCounts++;
        //-------------------------
        //NamesDecorator.makeNiceRemakeVertexes(vertexContainer.v.left, vertexContainer.v.right);
        //-------------------------
        vertexContainer.v = Substitution.remakeVertex(vertexContainer.v.left.left.propose, vertexContainer.v.right, vertexContainer.v.left.right);

    }

    //not changed
    /*
    private static Vertex tryGetNorm(Vertex input) {

        Vertex v = new Vertex(input);
        int curDfs;

        while (true) {
            //v = new NiceVertex(v);																//Change - Nice
            curDfs = dfsFindRedexes(v);
            if (curDfs < 0) {
                return v;
            }
            //System.out.println("Step : " + v);
            //System.out.println("curDfs = " + curDfs);
            DebugInformer.statisticsBigStep();
            if (curDfs == 0) {
                v = reductRedexRemakeVertex(v);
                continue;
            }
            Vertex t = v;
            while (true) {
                //System.out.println("T: " + t);
                //System.out.println("l : " + dfsFindRedexes(t.left) + "  r : " + dfsFindRedexes(t.right) + "   t : " + dfsFindRedexes(t));
                int x = dfsFindRedexes(t.left);
                if (x == 0) {
                    t.left = reductRedexRemakeVertex(t.left);
                    t.countHashAndCo(false);
                    break;
                }
                if (x > 0) {
                    t = t.left;
                    t.countHashAndCo(false);
                    continue;
                }
                x = dfsFindRedexes(t.right);
                if (x == 0) {
                    t.right = reductRedexRemakeVertex(t.right);
                    t.countHashAndCo(false);
                    break;
                }
                t = t.right;
                t.countHashAndCo(false);
            }
        }
    }*/

    private static Vertex tryGetNormRecursive(Vertex input) {
        VertexContainer vertexContainer = new VertexContainer(input);
        while (vertexContainer.v.dfs >= 0) {
            DebugInformer.statisticsBigStep();
            nextStepRemakeVertex(vertexContainer);
        }
        return vertexContainer.v;
    }

    private static void nextStepRemakeVertex(VertexContainer vertexContainer) {
        DebugInformer.stepCounts++;
        Vertex v = vertexContainer.v;
        if (v.dfs < 0) {
            return;
        }
        if (v.dfs == 0) {
            reductRedexRemakeVertex(vertexContainer);
        } else if (v.left.dfs >= 0) {
            VertexContainer innerContainer = new VertexContainer(v.left);
            nextStepRemakeVertex(innerContainer);
            v.left = innerContainer.v;
        } else {
            VertexContainer innerContainer = new VertexContainer(v.right);
            nextStepRemakeVertex(innerContainer);
            v.right = innerContainer.v;
        }
        v.countHashAndCo();
    }
}
