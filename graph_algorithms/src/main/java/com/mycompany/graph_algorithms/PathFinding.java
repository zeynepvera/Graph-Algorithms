package com.mycompany.graph_algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;


public class PathFinding {

    private Graph graph;

    public PathFinding(Graph graph) {
        this.graph = graph;
    }

    //BFS
    public List<Integer> findshortestpathbycities(int start, int end) {

        Queue<List<Integer>> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.getVerticescount() + 1];

        queue.add(Arrays.asList(start));
        visited[start] = true;

        while (!queue.isEmpty()) {

            List<Integer> path = queue.poll();
            int lastnode = path.get(path.size() - 1);

            if (lastnode == end) {

                return path;
            }

            for (Edge_ edge : graph.getNeighbors(lastnode)) {

                if (!visited[edge.to_city]) {

                    visited[edge.to_city] = true;
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(edge.to_city);
                    queue.add(newPath);

                }

            }

        }

        return new ArrayList<>();

    }

    //DİJKSTRA
    public List<Integer> findsbystandard(int start, int end) {

        int[] distances = new int[graph.getVerticescount() + 1];
        int[] previousvertices = new int[graph.getVerticescount() + 1];

        Arrays.fill(distances, Integer.MAX_VALUE); //infinity distance,because we are still dont have
        Arrays.fill(previousvertices, -1);
        distances[start] = 0;

        PriorityQueue<Edge_> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.distance));
        pq.add(new Edge_(start, 0));

        while (!pq.isEmpty()) {
            Edge_ currentedge = pq.poll();
            int current = currentedge.to_city;
            if (current == end) {
                break;
            }

            for (Edge_ edge : graph.getNeighbors(current)) {

                int neighborcity = edge.to_city;
                int newdistance = distances[current] + edge.distance;

                if (newdistance < distances[neighborcity]) {

                    distances[neighborcity] = newdistance;
                    previousvertices[neighborcity] = current;
                    pq.add(new Edge_(neighborcity, newdistance));

                }

            }

        }

        List<Integer> path = new ArrayList<>();
        for (int currentcity = end; currentcity != -1; currentcity = previousvertices[currentcity]) {
            path.add(currentcity);

        }

        Collections.reverse(path);

        return path.get(0) == start ? path : new ArrayList<>();
    }

    //formula euclidean(heuristic) tahmini mesafe
    private double heuristic(int node, int target, Map<Integer, double[]> coordinates) {

        double[] nodecoordinates = coordinates.get(node);
        double[] targetcoordinates = coordinates.get(target);

        return Math.sqrt(Math.pow(nodecoordinates[0] - targetcoordinates[0], 2) + Math.pow(nodecoordinates[1] - targetcoordinates[1], 2));

    }

    //A* algorithm
    public List<Integer> findsbyheuristic(int start, int end, Map<Integer, double[]> coordinates) {

        int[] distances = new int[graph.getVerticescount() + 1];
        int[] previous = new int[graph.getVerticescount() + 1];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        distances[start] = 0;

        PriorityQueue<Edge_> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.distance));
        pq.add(new Edge_(start, 0));

        while (!pq.isEmpty()) {
            Edge_ currentedge = pq.poll();
            int current = currentedge.to_city;

            if (current == end) {
                break;
            }

            for (Edge_ edge : graph.getNeighbors(current)) {

                int neighbor = edge.to_city;
                int newdistance = distances[current] + edge.distance;

                double heuristic = heuristic(neighbor, end, coordinates);
                double totalcost = newdistance + heuristic; //f= h(n)+g(n)

                if (newdistance < distances[neighbor]) {

                    distances[neighbor] = newdistance;
                    previous[neighbor] = current;
                    pq.add(new Edge_(neighbor, (int) totalcost));

                }

            }

        }

        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = previous[at]) {
            path.add(at);

        }
        Collections.reverse(path);

        return path.get(0) == start ? path : new ArrayList<>();
    }

}
