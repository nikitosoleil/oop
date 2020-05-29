import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

public class GraphTest {
    @Test
    public void constructionFromJsonTest() throws IOException {
        Graph graph = GraphFromJSON.getGraphFromResource("graph.json");
        assertEquals(10, graph.getVertices().size());
        assertEquals(30, graph.getEdges().size());
    }
}
