package com.vertexes;

import com.utils.Pair;

import java.util.HashMap;

/**
 * Created  by dima  on 22.10.14.
 */
public class MyCache {


	public static HashMap<Vertex, Vertex> doneMap = new HashMap<>();
	public static HashMap<Vertex, Integer> dfsMap = new HashMap<>();
	public static HashMap<Vertex, Vertex> niceMap = new HashMap<>();
	public static HashMap<Pair<Vertex, Vertex>, Pair<Vertex, Vertex>> nicePairsMap = new HashMap<>();

}