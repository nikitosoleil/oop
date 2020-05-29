import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GraphFromJSON {
    public static Graph getGraphFromResource(String jsonResource) throws IOException {
        ClassLoader classLoader = GraphFromJSON.class.getClassLoader();
        URL resource = classLoader.getResource(jsonResource);
        String jsonString = Files.readString(Paths.get(resource.getPath().substring(1)));
        return new Graph(jsonString);
    }
}
