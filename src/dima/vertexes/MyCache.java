package dima.vertexes;

import dima.utils.Pair;

import java.util.HashMap;

/**
 * Created  by dima  on 22.10.14.
 */
public class MyCache {

    public static HashMap<Vertex, Integer> dfsMap = new HashMap<>();
    public static HashMap<Vertex, Vertex> niceMap = new HashMap<>();
    public static HashMap<Vertex, Vertex> redexMap = new HashMap<>();
    public static HashMap<Vertex, Vertex> stepMap = new HashMap<>();
    public static HashMap<Pair<Vertex, Vertex>, Pair<Vertex, Vertex>> nicePairsMap = new HashMap<>();

}
