import java.util.*;

class Graph {
    Map<Node, List<Edge>> adj = new HashMap<>();

    public void addNode(Node node) {
        adj.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node from, Node to, int distance, int traffic) {
        adj.get(from).add(new Edge(to, distance, traffic));
    }

    public List<Edge> getNeighbors(Node node) {
        return adj.get(node);
    }
}