import org.json.JSONStringer;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class MySession {
    Session session;
    Graph graph;
    BellmanFord algorithm;

    MySession(Session session) {
        this.session = session;
        this.graph = new Graph(Paths.get("D:/University/OOP/untitled/src/main/resources/graph.json"));
        this.algorithm = new BellmanFord(graph);
        drawGraph(graph);
    }

    public void searchQuery(int fromId, int toId) {
        algorithm.computeDistances(graph.getVertices().get(fromId));
        List<Vertex> path = algorithm.reconstructPath(graph.getVertices().get(toId));
        drawPath(path);
    }

    void drawGraph(Graph graph) {
        JSONStringer stringer = new JSONStringer();
        stringer.object();
        stringer.key("type").value("graph");

        stringer.key("nVertices").value(String.valueOf(graph.getVertices().size()));
        for (Vertex v : graph.getVertices()) {
            stringer.key(v + "x").value(String.valueOf(v.getCoordinates().getX()));
            stringer.key(v + "y").value(String.valueOf(v.getCoordinates().getY()));
            stringer.key(v + "z").value(String.valueOf(v.getCoordinates().getZ()));
        }
        stringer.key("nEdges").value(String.valueOf(graph.getEdges().size()));
        for (Edge e : graph.getEdges()) {
            stringer.key(e + "to").value(String.valueOf(e.getTo().getId()));
            stringer.key(e + "from").value(String.valueOf(e.getFrom().getId()));
        }
        stringer.endObject();

        try {
            session.getBasicRemote().sendText(stringer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void drawPath(List<Vertex> path) {
        System.out.println("Found path: " + path);
        JSONStringer stringer = new JSONStringer();
        stringer.object();
        stringer.key("type").value("path");
        stringer.key("nVertices").value(String.valueOf(path.size()));

        for(int i = 0; i<path.size(); ++i) {
            Vertex v = path.get(i);
            stringer.key("v" + i).value(String.valueOf(v.getId()));
        }
        stringer.endObject();

        try {
            session.getBasicRemote().sendText(stringer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
