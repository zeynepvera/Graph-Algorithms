package com.mycompany.graph_algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private int vertices;
    private Map< Integer, List<Edge_>> edges;

    public Graph(int vertices) {

        this.vertices = vertices;
        this.edges = new HashMap<>();
    }

    public void addEdge(int city_a, int city_b, int distance) {

        //has a list of edges or not
        if (!edges.containsKey(city_a)) {

            edges.put(city_a, new ArrayList<>());

        }
        edges.get(city_a).add(new Edge_(city_b, distance));

        if (!edges.containsKey(city_b)) {
            edges.put(city_b, new ArrayList<>());

        }
        edges.get(city_b).add(new Edge_(city_a, distance));

    }

    public List<Edge_> getNeighbors(int node) {

        if (edges.containsKey(node)) {

            return edges.get(node);

        } else {

            List<Edge_> emptyList = new ArrayList<>();
            edges.put(node, emptyList);
            return emptyList;
        }

    }

    public int getVerticescount() {

        return vertices;
    }

}
