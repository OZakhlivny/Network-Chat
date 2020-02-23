package lesson1;

public class RunningTrack implements Let {
    public static final int MAX_DISTANCE = 100;

    private int distance;

    public RunningTrack(){
        this.distance = MAX_DISTANCE;
    }

    public RunningTrack(int distance){
        this.distance = distance;
    }

    @Override
    public boolean overcome(Activity object) {
        boolean result = false;
        if(this.distance <= object.run()){
            result = true;
            System.out.println("Препятствие Беговая дорожка преодолено");
        } else System.out.println("Препятствие Беговая дорожка не преодолено");
        return result;
    }
}

