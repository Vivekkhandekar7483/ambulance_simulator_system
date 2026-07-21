class TrafficSignal {
    Node location;
    String state; // RED or GREEN
    int timer;

    public TrafficSignal(Node location, String state, int timer) {
        this.location = location;
        this.state = state;
        this.timer = timer;
    }

    public int getDelay() {
        return state.equals("RED") ? timer : 0;
    }
}