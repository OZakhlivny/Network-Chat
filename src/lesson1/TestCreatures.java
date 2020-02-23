package lesson1;

public class TestCreatures {

    public static void main(String[] args){

        // Во всех создаваемых объектах используем значения по умолчанию
        Activity activityCreatures[] = {new Human(), new Robot(), new Cat()};
        Let lets[] = {new RunningTrack(), new Wall()};

        for(Activity activityCreature : activityCreatures) {
            System.out.println(activityCreature.getInfo());
            for(int i = 0; i < lets.length; i++)
                if (!lets[i].overcome(activityCreature)) break;
        }
    }
}
