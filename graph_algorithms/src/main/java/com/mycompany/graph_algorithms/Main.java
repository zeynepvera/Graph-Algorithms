package com.mycompany.graph_algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        int numberof_city = scan.nextInt();
        int numberof_vertices = scan.nextInt();

        Graph graph = new Graph(numberof_city);

        Map<Integer, double[]> coordinates = new HashMap<>();
        //get the coordinet info
        for (int i = 1; i <= numberof_city; i++) {

            double latitude = scan.nextDouble();
            double longitude = scan.nextDouble();
            coordinates.put(i, new double[]{latitude, longitude});

        }
        
        //road between cities and distances
        for (int i = 0; i < numberof_vertices; i++) {

            int u = scan.nextInt();
            int v = scan.nextInt();
            int distance = scan.nextInt();
            graph.addEdge(u, v, distance);

        }

        int start = scan.nextInt();
        int end = scan.nextInt();

        PathFinding pf = new PathFinding(graph);

        //measure bfs time
        long starttime = System.nanoTime();
        List<Integer> bfspath = pf.findshortestpathbycities(start, end); //find way
        long bfstime = System.nanoTime() - starttime;

        System.out.println("bfs path: " + bfspath);
        System.out.println("bfs time: " + bfstime / 1e6 + "ms");

        //Measure Dijkstra
        starttime = System.nanoTime();
        List<Integer> dijkstrapath = pf.findsbystandard(start, end);
        long dijkstratime = System.nanoTime() - starttime;

        System.out.println("dijkstra path: " + dijkstrapath);
        System.out.println("dijkstra time: " + dijkstratime / 1e6 + "ms");

        //Measure A*
        starttime = System.nanoTime();
        List<Integer> astarpath = pf.findsbyheuristic(start, end, coordinates);
        long astarttime = System.nanoTime() - starttime;

        System.out.println("A* path: " + astarpath);
        System.out.println("A* time: " + astarttime / 1e6 + "ms");

    }

}
