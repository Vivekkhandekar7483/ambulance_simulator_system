package com.example.demo;

public class TrafficSignal {

    private String node;

    private String state;

    public TrafficSignal(
            String node,
            String state
    ) {

        this.node = node;
        this.state = state;
    }

    public String getNode() {
        return node;
    }

    public String getState() {
        return state;
    }
}