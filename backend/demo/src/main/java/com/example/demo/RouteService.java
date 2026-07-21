package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteService {

    // 📍 Nodes
    private final Map<String, Node> nodes =
            new HashMap<>();

    // 🛣 Graph
    private final Map<String, List<Edge>> graph =
            new HashMap<>();

    // 🚦 Signals
    private final Map<String, TrafficSignal> signals =
            new HashMap<>();

    public RouteService() {

        initializeNodes();

        initializeSignals();

        initializeRoads();
    }

    // 📍 Create Nodes
    private void initializeNodes() {

        nodes.put("A",
                new Node("A",
                        15.8600,
                        74.5000));

        nodes.put("B",
                new Node("B",
                        15.8570,
                        74.4970));

        nodes.put("C",
                new Node("C",
                        15.8530,
                        74.4940));

        nodes.put("D",
                new Node("D",
                        15.8500,
                        74.4920));

        nodes.put("E",
                new Node("E",
                        15.8470,
                        74.4900));

        nodes.put("F",
                new Node("F",
                        15.8450,
                        74.4880));

        nodes.put("G",
                new Node("G",
                        15.8520,
                        74.5000));

        nodes.put("H",
                new Node("H",
                        15.8480,
                        74.5040));
        nodes.put("I",
                new Node("I",
                        15.8565,
                        74.4985));

        nodes.put("J",
                new Node("J",
                        15.8540,
                        74.4960));

        nodes.put("K",
                new Node("K",
                        15.8515,
                        74.4935));

        nodes.put("L",
                new Node("L",
                        15.8490,
                        74.4910));

        nodes.put("M",
                new Node("M",
                        15.8475,
                        74.4890));
    }

    // 🚦 Signals
    private void initializeSignals() {

        signals.put("B",
                new TrafficSignal("B", "RED"));

        signals.put("D",
                new TrafficSignal("D", "GREEN"));

        signals.put("G",
                new TrafficSignal("G", "YELLOW"));

        signals.put("J",
                new TrafficSignal("J", "RED"));

        signals.put("K",
                new TrafficSignal("K", "GREEN"));

        signals.put("L",
                new TrafficSignal("L", "YELLOW"));
    }

    // 🛣 Roads
    private void initializeRoads() {

        addRoad("A", "B", 4);

        addRoad("B", "C", 5);

        addRoad("C", "D", 3);

        addRoad("D", "E", 4);

        addRoad("E", "F", 2);

        // Alternate Routes
        addRoad("A", "G", 6);

        addRoad("G", "H", 4);

        addRoad("H", "F", 5);

        addRoad("B", "G", 3);

        addRoad("C", "H", 6);

        addRoad("D", "H", 5);
        addRoad("A", "I", 2);

        addRoad("I", "J", 2);

        addRoad("J", "K", 2);

        addRoad("K", "L", 2);

        addRoad("L", "M", 2);

        addRoad("M", "F", 2);

        addRoad("B", "J", 3);

        addRoad("C", "K", 2);

        addRoad("D", "L", 3);
    }

    // ➕ Add Road
    private void addRoad(
            String from,
            String to,
            int weight
    ) {

        // 🚦 Traffic-aware weight
        if (signals.containsKey(to)) {

            String state =
                    signals.get(to).getState();

            if (state.equals("RED")) {

                weight += 10;

            } else if (state.equals("YELLOW")) {

                weight += 2;
            }
        }

        graph.putIfAbsent(
                from,
                new ArrayList<>());

        graph.get(from)
                .add(new Edge(to, weight));

        // Two-way roads
        graph.putIfAbsent(
                to,
                new ArrayList<>());

        graph.get(to)
                .add(new Edge(from, weight));
    }

    // 🚑 Dijkstra Algorithm
    public List<Node> shortestPath(
            String start,
            String end
    ) {

        Map<String, Integer> distance =
                new HashMap<>();

        Map<String, String> previous =
                new HashMap<>();

        PriorityQueue<String> pq =
                new PriorityQueue<>(
                        Comparator.comparingInt(
                                distance::get
                        )
                );

        for (String node : nodes.keySet()) {

            distance.put(
                    node,
                    Integer.MAX_VALUE
            );
        }

        distance.put(start, 0);

        pq.add(start);

        while (!pq.isEmpty()) {

            String current = pq.poll();

            for (Edge edge :
                    graph.get(current)) {

                int newDist =
                        distance.get(current)
                                + edge.getWeight();

                if (newDist <
                        distance.get(
                                edge.getDestination()
                        )) {

                    distance.put(
                            edge.getDestination(),
                            newDist
                    );

                    previous.put(
                            edge.getDestination(),
                            current
                    );

                    pq.add(
                            edge.getDestination()
                    );
                }
            }
        }

        // 🛣 Build Path
        List<Node> path =
                new ArrayList<>();

        String step = end;

        while (step != null) {

            path.add(nodes.get(step));

            step = previous.get(step);
        }

        Collections.reverse(path);

        return path;
    }

    // 📍 Get Signals
    public Collection<TrafficSignal>
    getSignals() {

        return signals.values();
    }

    public int calculatePathCost(
            List<Node> path
    ) {

        int totalCost = 0;

        for (int i = 0;
             i < path.size() - 1;
             i++) {

            String current =
                    path.get(i).getName();

            String next =
                    path.get(i + 1).getName();

            List<Edge> edges =
                    graph.get(current);

            for (Edge edge : edges) {

                if (edge.getDestination()
                        .equals(next)) {

                    totalCost +=
                            edge.getWeight();

                    break;
                }
            }
        }

        return totalCost;
    }
}