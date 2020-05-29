import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BellmanFord {
    private static final double INF = 1e18;
    private final Graph graph;
    private final Map<Vertex, Double> distances = new HashMap<>();
    private final Map<Vertex, Vertex> previous = new HashMap<>();

    public BellmanFord(Graph graph) {
        this.graph = graph;
    }

    public Map<Vertex, Double> getDistances() {
        return distances;
    }

    public void computeDistances(Vertex source) {
        for (Vertex v : graph.getVertices())
            distances.put(v, INF);
        distances.put(source, 0.);
        boolean updated = true;
        for (int iteration = 0; iteration < graph.getVertices().size() && updated; ++iteration) {
            updated = false;
            for (Edge e : graph.getEdges()) {
                double weight = e.getWeight();

                Vertex from = e.getFrom();
                Vertex to = e.getTo();

                double dFrom = distances.get(from);
                double dTo = distances.get(to);

                if (dFrom + weight < dTo) {
                    updated = true;
                    distances.put(to, dFrom + weight);
                    previous.put(to, from);
                }
            }
        }
        return;
    }

    public LinkedList<Vertex> reconstructPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex current = target;

        if (distances.get(current) == INF) {
            return null;
        }
        path.add(current);
        while (distances.get(current) != 0) {
            current = previous.get(current);
            path.add(current);
        }

        Collections.reverse(path);
        return path;
    }
}
