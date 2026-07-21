class Edge {
    Node destination;
    int distance;
    int traffic;

    public Edge(Node destination, int distance, int traffic) {
        this.destination = destination;
        this.distance = distance;
        this.traffic = traffic;
    }
}