package by.zapolski;

public class MyFile {

    //private static int count = 0;
    //private int id = count++;
    private int id;
    private String parentDir;
    private String name;
    private long lenght;

    public MyFile(String parentDir, String name, long lenght) {
        this.parentDir = parentDir;
        this.name = name;
        this.lenght = lenght;
    }

    public MyFile(int id, String parentDir, String name, long lenght) {
        this.id = id;
        this.parentDir = parentDir;
        this.name = name;
        this.lenght = lenght;
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

    public long getLenght() {
        return lenght;
    }

    public void setLenght(long lenght) {
        this.lenght = lenght;
    }

    @Override
    public String toString() {
        return "MyFile{" +
                "id=" + id +
                ", parentDir='" + parentDir + '\'' +
                ", name='" + name + '\'' +
                ", lenght=" + lenght +
                '}';
    }
}
