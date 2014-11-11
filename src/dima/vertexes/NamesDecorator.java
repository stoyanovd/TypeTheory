package dima.vertexes;

import dima.supports.DebugInformer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created  by dima  on 22.10.14.
 */
public class NamesDecorator {

    private Vertex nice;
    private Vertex nice2;
    private Set<String> freeProposes;
    private int curCount;

    public static void makeNiceRemakeVertex(Vertex v) {
        new NamesDecorator(v);
    }

    public static void makeNiceRemakeVertexes(Vertex v, Vertex t) {
        new NamesDecorator(v, t);
    }

    private NamesDecorator(Vertex v) {
        DebugInformer.namesDecoratorCounts++;
        freeProposes = new HashSet<>(Vertex.getFreeProposes(v));
        nice = v;
        curCount = 0;

        makeNiceNames(nice, new HashMap<>());
    }

    private NamesDecorator(Vertex v, Vertex t) {
        DebugInformer.namesDecoratorPairsCounts++;
        freeProposes = new HashSet<>(Vertex.getFreeProposes(v));
        freeProposes.addAll(Vertex.getFreeProposes(t));
        nice = v;
        curCount = 0;
        makeNiceNames(nice, new HashMap<>());
        nice2 = t;
        makeNiceNames(nice2, new HashMap<>());
    }

    public Vertex getNewNice() {
        return new Vertex(nice);
    }

    public Vertex getNewNice2() {
        return new Vertex(nice2);
    }

    public boolean nicesAreEquals() {
        return nice.equals(nice2);
    }

    private void makeNiceNames(Vertex t, HashMap<String, String> intToName) {

        if (t == null) {
            return;
        }
        switch (t.operation) {
            case 'V': {
                if (intToName.containsKey(t.propose)) {
                    t.propose = intToName.get(t.propose);
                    t.countHashAndCo();
                    return;
                }
                if (!freeProposes.contains(t.propose)) {
                    throw new NullPointerException("We don't know this propose: " + t.propose);
                }
            }
            case 'A': {
                makeNiceNames(t.left, intToName);
                makeNiceNames(t.right, intToName);
                t.countHashAndCo();
                return;
            }
            case 'L': {
                String oldKey = t.left.propose;
                String oldValue = null;
                if (intToName.containsKey(oldKey)) {
                    oldValue = intToName.get(oldKey);
                }

                String s;
                do {
                    s = "x" + curCount;
                    curCount++;
                }
                while (freeProposes.contains(s));
                intToName.put(oldKey, s);

                makeNiceNames(t.left, intToName);
                makeNiceNames(t.right, intToName);
                t.countHashAndCo();
                if (oldValue == null) {
                    intToName.remove(oldKey);
                } else {
                    intToName.put(oldKey, oldValue);
                }
            }
        }
    }
}
