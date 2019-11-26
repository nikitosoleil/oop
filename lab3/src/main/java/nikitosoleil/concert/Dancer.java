package nikitosoleil.concert;

public class Dancer {
    private String name;
    private Integer age;
    private Integer experience;

    public Dancer() {
    }

    public Dancer(String name, Integer age, Integer experience) {
        setName(name);
        setAge(age);
        setExperience(experience);
    }

    @Override
    public String toString() {
        try {
            return String.format(
                    " -Name: %s\n" +
                            " -Age: %s\n" +
                            " -Experience: %s\n",
                    name, age, experience);
        } catch (Exception e) {
            return "";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }


}
