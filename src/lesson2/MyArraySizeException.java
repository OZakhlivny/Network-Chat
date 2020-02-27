package lesson2;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException{
    MyArraySizeException(String errorString)
    {
        super(String.format("Ошибка размерности массива! %s", errorString));
    }
}
