package lesson1;

public class Cat implements Activity {
    public static final int MAX_DISTANCE = 50;
    public static final int MAX_HEIGHT = 1;

    private String name;
    private int distance;
    private int height;

    public Cat(){
        this.name = "Без имени";
        this.distance = MAX_DISTANCE;
        this.height = MAX_HEIGHT;
    }

    public Cat(String name, int distance, int height){
        this.name = name;
        this.distance = distance;
        this.height = height;
    }

    @Override
    public String getInfo(){
        return "Кот: " + name;
    }

    @Override
    public int run() {
        return distance;
    }

    @Override
    public int jump() {
        return height;
    }
}