package lesson1;

public class Robot implements Activity {
    public static final int MAX_DISTANCE = 200;
    public static final int MAX_HEIGHT = 1;


    private String name;
    private int distance;
    private int height;


    public Robot(){
        this.name = "Без имени";
        this.distance = MAX_DISTANCE;
        this.height = MAX_HEIGHT;
    }

    public Robot(String name, int distance, int height){
        this.name = name;
        this.distance = distance;
        this.height = height;
    }

    @Override
    public String getInfo(){
        return "Робот: " + name;
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
