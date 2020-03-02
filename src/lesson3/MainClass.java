package lesson3;

import java.util.*;

public class MainClass {
    public static final int NUMBER_OF_WORDS = 20;

    public static void main(String[] args){
        System.out.println("Задание №1");
        task1();
        System.out.println("Задание №2");
        task2();

    }

    public static void task1(){
        String[] wordsArray = new String[NUMBER_OF_WORDS];

        fillTheArrayOfWords(wordsArray);
        System.out.println("Массив строк с дубликатами:");
        System.out.println(Arrays.toString(wordsArray));

        Set<String> treeSet = new TreeSet<>();
        Collections.addAll(treeSet, wordsArray);
        System.out.println("Список уникальных строк:");
        System.out.println(treeSet);

        Map<String, Integer> treeMap = new TreeMap<>();

        for(String keyString : wordsArray) {
            if (treeMap.containsKey(keyString)) treeMap.put(keyString, treeMap.get(keyString) + 1);
            else treeMap.put(keyString, 1);
        }
        System.out.println("Расчет количества повторений строк в массиве:");
        System.out.println(treeMap);
    }

    public static void fillTheArrayOfWords(String[] array){
        for(int i = 0; i < NUMBER_OF_WORDS; i++) {
            array[i] = String.format("Строка вида %d", (int)(Math.random() * 5));
        }
    }

    public static void task2(){
        PhoneBook phoneBook = new PhoneBook();

        phoneBook.add("+7(999)123-123-12", "Иванов");
        phoneBook.add("+7(999)321-321-21", "Петров");
        phoneBook.add("+7(999)111-222-33", "Сидоров");
        phoneBook.add("+7(999)555-666-77", "Иванов");
        phoneBook.add("+7(999)333-111-77", "Иванов");
        phoneBook.add("+7(999)888-321-21", "Петров");

        System.out.println("Телефонные номера под фамилией Иванов: " + phoneBook.get("Иванов"));
        System.out.println("Телефонные номера под фамилией Петров: " + phoneBook.get("Петров"));
        System.out.println("Телефонные номера под фамилией Сидоров: " + phoneBook.get("Сидоров"));

    }

}

