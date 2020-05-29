import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BellmanFordTest {
    private Graph graph;
    private BellmanFord algorithm;

    @Before
    public void setupAlgorithm() throws IOException {
        graph = GraphFromJSON.getGraphFromResource("graph.json");
        algorithm = new BellmanFord(graph);
    }

    @Test
    public void distancesTest() {
        List<Vertex> vertices = graph.getVertices();
        algorithm.computeDistances(vertices.get(1));

        assertEquals(80., algorithm.getDistances().get(vertices.get(3)), 1e-6);
        assertEquals(50., algorithm.getDistances().get(vertices.get(0)), 1e-6);
    }

    @Test
    public void pathTest() {
        List<Vertex> vertices = graph.getVertices();
        algorithm.computeDistances(vertices.get(8));

        List<Vertex> path = algorithm.reconstructPath(vertices.get(7));
        List<Vertex> correctPath = Arrays.asList(vertices.get(8), vertices.get(6), vertices.get(5), vertices.get(7));
        assertEquals(correctPath, path);

        path = algorithm.reconstructPath(vertices.get(2));
        correctPath = Arrays.asList(vertices.get(8), vertices.get(4), vertices.get(1), vertices.get(2));
        assertEquals(correctPath, path);

        path = algorithm.reconstructPath(vertices.get(10));
        assertNull(path);
    }
}
