package lesson5;

public class TestThreadsClass {
    static final int SIZE = 10000000;


    public static void main(String[] args) throws InterruptedException {
        float[] array = new float[SIZE];
        //заполнение массива единицами
        arrayFillByOnes(array);
        //расчет элементов массива прямым проходом
        arrayStraightPass(array);
        // "Обнуление" работы предыдущего метода, перезаполнение массива единицами
        arrayFillByOnes(array);
        //расчет элементов массива методом с использованием двух потоков
        arrayDoubleThreadsPass(array);

    }

    public static void arrayStraightPass(float[] array){
        long startTime = System.currentTimeMillis();

        arrayCalculateElements(array);
        System.out.printf("Straight pass method time is %d ms\n", System.currentTimeMillis() - startTime);
    }

    public static void arrayDoubleThreadsPass(float[] array) throws InterruptedException {
        int halfElementsOfArray = SIZE / 2;
        float[] firstHalfOfArray = new float[halfElementsOfArray];
        float[] secondHalfOfArray = new float[halfElementsOfArray];
        Thread thread1 = new Thread(() -> arrayCalculateElements(firstHalfOfArray));
        Thread thread2 = new Thread(() -> arrayCalculateElements(secondHalfOfArray));

        long startTime = System.currentTimeMillis();

        System.arraycopy(array, 0, firstHalfOfArray, 0, halfElementsOfArray);
        System.arraycopy(array, halfElementsOfArray, secondHalfOfArray, 0, halfElementsOfArray);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.arraycopy(firstHalfOfArray, 0, array, 0, halfElementsOfArray);
        System.arraycopy(secondHalfOfArray, 0, array, halfElementsOfArray, halfElementsOfArray);

        System.out.printf("Double threads pass method time is %d ms\n", System.currentTimeMillis() - startTime);

    }

    public static void arrayFillByOnes(float[] array){
        for(int i = 0; i < array.length; i++) array[i] = 1;
    }

    public static void arrayCalculateElements(float[] array){
        for(int i = 0; i < array.length; i++)
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    }
}
