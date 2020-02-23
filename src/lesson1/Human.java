package lesson1;

public class Human implements Activity {
    public static final int MAX_DISTANCE = 100;
    public static final int MAX_HEIGHT = 2;

    private String name;
    private int distance;
    private int height;

    public Human(){
        this.name = "Без имени";
        this.distance = MAX_DISTANCE;
        this.height = MAX_HEIGHT;
    }

    public Human(String name, int distance, int height){
        this.name = name;
        this.distance = distance;
        this.height = height;
    }

    @Override
    public String getInfo(){
        return "Человек: " + name;
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

