public class Edge {
    private final int id;
    private final Vertex from;
    private final Vertex to;
    private final double weight;

    public Edge(int id, Vertex from, Vertex to) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.weight = squaredDistance(from, to);
    }

    private static double squaredDistance(Vertex u, Vertex v) {
        return u.getCoordinates().distance(v.getCoordinates());
    }

    public int getId() {
        return id;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "e" + id;
    }
}
