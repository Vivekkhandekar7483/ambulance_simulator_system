package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class RouteController {

    private final RouteService routeService;

    public RouteController(
            RouteService routeService
    ) {

        this.routeService = routeService;
    }

    @GetMapping("/route")
    public Map<String, Object> getRoute() {

        Map<String, Object> response =
                new HashMap<>();

        List<Node> path =
                routeService.shortestPath(
                        "A",
                        "F"
                );
        int totalCost =
                routeService.calculatePathCost(
                        path
                );

        response.put("route", path);

        response.put(
                "eta",
                totalCost + " Minutes"
        );

        response.put("signals",
                routeService.getSignals());

        return response;
    }
}