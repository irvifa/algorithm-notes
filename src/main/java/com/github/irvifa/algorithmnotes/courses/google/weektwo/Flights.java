package com.github.irvifa.algorithmnotes.courses.google.weektwo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

class Graph {
    Map<String, List<Flight>> edges;

    public Graph() {
        this.edges = new HashMap<>();
    }

    public void addEdge(String source, Flight flight) {
        List<Flight> availableRoutes;
        if (!edges.containsKey(source)) {
            availableRoutes = new ArrayList<>();
        } else {
            availableRoutes = edges.get(source);
        }
        availableRoutes.add(flight);
        edges.put(source, availableRoutes);
    }

    void findConnections(String src, String dst, int limit) {
        PriorityQueue<Path> q = new PriorityQueue<>(Comparator.comparing((Path p)->p.price)
        .thenComparing(p->p.routes.size())
        .thenComparing(p->p.toString()));
        PriorityQueue<Path> res = new PriorityQueue<>(Comparator.comparing((Path p)->p.price)
                .thenComparing(p->p.routes.size())
                .thenComparing(p->p.toString()));
        int n = limit;

        Path path = new Path();
        path.price = 0L;
        List<String> routes = new LinkedList<>();
        routes.add(src);
        path.routes = routes;
        q.add(path);
        while (!q.isEmpty()) {
            path = q.peek();
            routes = path.routes;
            q.poll();
            String last = routes.get(path.routes.size() - 1);

            if (last.equals(dst)) {
                res.add(path);
            }

            if (edges.containsKey(last)) {
                for (Flight flight : edges.get(last)) {
                    if (!routes.contains(flight.arrives)) {
                        Path newPath = new Path();
                        long newPrice = path.price + flight.price;
                        List<String> newPathRoutes = new LinkedList<>(routes);
                        newPathRoutes.add(flight.arrives);
                        newPath.price = newPrice;
                        newPath.routes = newPathRoutes;
                        q.add(newPath);
                    }
                }
            }
        }

        while (!res.isEmpty() && n > 0) {
            path = res.peek();
            routes = path.routes;
            res.poll();
            StringBuffer sb = new StringBuffer();
            for (String currentAirport : routes) {
                sb.append(currentAirport + " ");
            }
            sb.append(path.price);
            System.out.println(sb.toString());
            n--;
        }

        if (limit == n) {
            System.out.print("<no solution>");
        }
    }
}

class Flight {
    public String departs;
    public String arrives;
    public int price;

    public Flight(String departs, String arrives, int price) {
        this.departs = departs;
        this.arrives = arrives;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.join(" ", new String[]{this.departs, this.arrives, String.valueOf(this.price)});
    }
}

class Path {
    List<String> routes;
    Long price;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String r: routes) {
            sb.append(r);
        }
        sb.append(price);
        return sb.toString();
    }
}

public class Flights {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph flights = new Graph();

        int numberOfFlights = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < numberOfFlights; i++) {
            String[] line = sc.nextLine().trim().split(" ");
            String source = line[0];
            String dest = line[1];
            int price = Integer.parseInt(line[2].trim());
            Flight newFlight = new Flight(source, dest, price);
            flights.addEdge(source, newFlight);
        }

        int limit = Integer.parseInt(sc.nextLine());

        String[] sourceDestinationPair = sc.nextLine().trim().split(" ");
        String source = sourceDestinationPair[0];
        String destination = sourceDestinationPair[1];
        flights.findConnections(source, destination, limit);
    }
}
