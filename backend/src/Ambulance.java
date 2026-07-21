class Ambulance {
    String id;
    Node current;
    Node destination;
    int severity;

    public Ambulance(String id, Node current, Node destination, int severity) {
        this.id = id;
        this.current = current;
        this.destination = destination;
        this.severity = severity;
    }
}