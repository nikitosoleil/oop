package nikitosoleil.concert;


import java.util.ArrayList;
import java.util.List;

public class Dance {
    private String id;
    private Type type;
    private Scene scene;
    private NumberOfDancers numberOfDancers;
    private Music music;
    private List<Dancer> dancers;
    private Integer number;

    public Dance() {
        dancers = new ArrayList<>();
    }

    public void addDancer(Dancer dancer) {
        dancers.add(dancer);
    }

    @Override
    public String toString() {
        try {
            StringBuilder builder = new StringBuilder();
            if (id!=null)
                builder.append("Id: ").append(id.toString()).append("\n");
            builder.append("Type: ").append(type.toString()).append("\n");
            builder.append("Scene: ").append(scene.toString()).append("\n");
            builder.append("Number Of Dancers: ").append(numberOfDancers.toString()).append("\n");
            builder.append("Music: ").append(music.toString()).append("\n");

            builder.append("Dancers: \n");
            for (Dancer dancer : dancers) {
                builder.append(" ________\n");
                builder.append(dancer.toString());
            }

            builder.append("Number: ").append(number.toString()).append("\n");
            return builder.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public NumberOfDancers getNumberOfDancers() {
        return numberOfDancers;
    }

    public void setNumberOfDancers(NumberOfDancers numberOfDancers) {
        this.numberOfDancers = numberOfDancers;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public List<Dancer> getDancers() {
        return dancers;
    }

    public void setDancers(List<Dancer> dancers) {
        this.dancers = dancers;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
