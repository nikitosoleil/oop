import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Graph {
    private final List<Vertex> vertices = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    public Graph(String jsonString) {
        constructFromJson(jsonString);
    }

    public Graph(Path path) {
        try {
            String data = Files.readString(path);
            constructFromJson(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void constructFromJson(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);

        HashMap<Integer, Vertex> idToVertex = new HashMap<>();
        JSONArray jsonVertices = jsonObject.getJSONArray("vertices");
        for (int i = 0; i < jsonVertices.length(); ++i) {
            JSONObject jsonV = jsonVertices.getJSONObject(i);
            int id = jsonV.getInt("id");
            Vertex v = new Vertex(i, jsonV.getInt("x"), jsonV.getInt("y"), jsonV.getInt("z"));
            idToVertex.put(id, v);
            vertices.add(v);
        }

        JSONArray jsonEdges = jsonObject.getJSONArray("edges");
        for (int i = 0; i < jsonEdges.length(); ++i) {
            JSONObject jsonE = jsonEdges.getJSONObject(i);
            int fromId = jsonE.getInt("from"), toId = jsonE.getInt("to");
            Edge e = new Edge(i << 1, idToVertex.get(fromId), idToVertex.get(toId));
            edges.add(e);
            e = new Edge(i << 1 | 1, idToVertex.get(toId), idToVertex.get(fromId));
            edges.add(e);
        }
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
