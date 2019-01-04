package by.zapolski;

public class MyDirectory {

    //private static int count = 0;
    //private int id = count++;

    private int id;
    private String parentDir;
    private String name;

    public MyDirectory(String parentDir, String name) {
        this.parentDir = parentDir;
        this.name = name;
    }

    public MyDirectory(int id, String parentDir, String name) {
        this.id = id;
        this.parentDir = parentDir;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentDir() {
        return parentDir;
    }

    public void setParentDir(String parentDir) {
        this.parentDir = parentDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyDirectory{" +
                "id=" + id +
                ", parentDir='" + parentDir + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
