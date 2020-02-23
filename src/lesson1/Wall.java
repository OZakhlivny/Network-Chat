package lesson1;

public class Wall implements Let {
    public static final int MAX_HEIGHT = 2;

    private int height;

    public Wall(){
        this.height = MAX_HEIGHT;
    }

    public Wall(int height){
        this.height = height;
    }

    @Override
    public boolean overcome(Activity object) {
        boolean result = false;
        if(this.height <= object.jump()){
            result = true;
            System.out.println("Препятствие Стена преодолено");
        } else System.out.println("Препятствие Стена не преодолено");
        return result;
    }
}

