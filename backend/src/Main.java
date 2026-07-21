import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Create nodes
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");

        // Create graph
        Graph graph = new Graph();
        graph.addNode(A);
        graph.addNode(B);
        graph.addNode(C);
        graph.addNode(D);

        graph.addEdge(A, B, 5, 2);
        graph.addEdge(A, C, 10, 1);
        graph.addEdge(B, D, 3, 3);
        graph.addEdge(C, D, 2, 2);

        // Traffic signals
        Map<Node, TrafficSignal> signals = new HashMap<>();
        signals.put(B, new TrafficSignal(B, "RED", 30));
        signals.put(C, new TrafficSignal(C, "GREEN", 0));

        // Ambulances
        Ambulance a1 = new Ambulance("A1", A, D, 5);
        Ambulance a2 = new Ambulance("A2", A, D, 3);

        // Show best route for one ambulance
        System.out.println("===== SINGLE AMBULANCE ROUTE =====");
        List<Node> route = getPath(graph, signals, A, D);
        printRoute(route);

        // Simulate movement
        System.out.println("\n===== SIMULATION =====");
        simulateMovement(graph, signals, a1);

        // Priority decision
        System.out.println("\n===== PRIORITY SYSTEM =====");
        Ambulance priority = decidePriority(a1, a2);
        System.out.println("Signal turned GREEN for " + priority.id);
    }

    // ROUTE FINDING (Dijkstra)
    public static List<Node> getPath(Graph graph,
                                    Map<Node, TrafficSignal> signals,
                                    Node start,
                                    Node dest) {

        Map<Node, Integer> cost = new HashMap<>();
        Map<Node, Node> parent = new HashMap<>();

        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(cost::get));

        cost.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            for (Edge edge : graph.getNeighbors(current)) {

                int delay = 0;
                if (signals.containsKey(edge.destination)) {
                    delay = signals.get(edge.destination).getDelay();
                }

                int newCost = cost.get(current)
                        + edge.distance
                        + edge.traffic
                        + delay;

                if (!cost.containsKey(edge.destination)
                        || newCost < cost.get(edge.destination)) {

                    cost.put(edge.destination, newCost);
                    parent.put(edge.destination, current);
                    pq.add(edge.destination);
                }
            }
        }

        List<Node> path = new ArrayList<>();
        Node step = dest;

        while (step != null) {
            path.add(step);
            step = parent.get(step);
        }

        Collections.reverse(path);
        return path;
    }

    // PRINT ROUTE
    public static void printRoute(List<Node> path) {
        System.out.print("Best Route: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println();
    }

    // SIMULATION
    public static void simulateMovement(Graph graph,
                                        Map<Node, TrafficSignal> signals,
                                        Ambulance amb) {

        Node current = amb.current;

        while (!current.equals(amb.destination)) {

            System.out.println("Ambulance at " + current);

            List<Node> path = getPath(graph, signals, current, amb.destination);

            if (path.size() < 2) return;

            Node next = path.get(1);

            System.out.println("Moving to " + next);

            if (signals.containsKey(next)) {
                TrafficSignal s = signals.get(next);

                if (Math.random() < 0.5) {
                    s.state = "RED";
                    s.timer = 20;
                    System.out.println("Signal at " + next + " turned RED!");
                }
            }

            current = next;

            try { Thread.sleep(1000); } catch (Exception e) {}
        }

        System.out.println("Ambulance reached destination!");
    }

    // PRIORITY LOGIC
    public static Ambulance decidePriority(Ambulance a1, Ambulance a2) {

        System.out.println("Comparing ambulances...");
        System.out.println(a1.id + " severity: " + a1.severity);
        System.out.println(a2.id + " severity: " + a2.severity);

        if (a1.severity > a2.severity) return a1;
        return a2;
    }
}