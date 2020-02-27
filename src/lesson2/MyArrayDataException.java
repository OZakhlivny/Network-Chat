package lesson2;

public class MyArrayDataException extends NumberFormatException {
    public MyArrayDataException(String errorString){

        super(String.format("Ошибка данных массива! %s", errorString));
    }
}
