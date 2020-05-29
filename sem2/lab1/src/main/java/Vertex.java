import javafx.geometry.Point3D;

public class Vertex {
    private final int id;
    public final Point3D coordinates;

    public Vertex(int id, int x, int y, int z) {
        this.id = id;
        coordinates = new Point3D(x, y, z);
    }

    public int getId() {
        return id;
    }

    public Point3D getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "v" + id;
    }


}
