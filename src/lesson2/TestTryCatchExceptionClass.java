package lesson2;

import static java.lang.Integer.parseInt;

public class TestTryCatchExceptionClass {

    public static final int ARRAY_ROWS_COUNT = 4;
    public static final int ARRAY_COLUMNS_COUNT = 4;


    public static void main(String[] args){
        /* моделируем все возможные ситуации:
            1 - корректный массив
            2 - размерность 3х4
            3 - размерность второго подмассива 3
            4 - ошибка преобразования типов
        */
        String[][][] arrays = {{{"1","2","3","4"}, {"1","2","3","4"}, {"1","2","3","4"}, {"1","2","3","4"}},
                {{"1","2","3","4"}, {"1","2","3","4"}, {"1","2","3","4"}},
                {{"1","2","3","4"}, {"1","2","3"}, {"1","2","3","4"}, {"1","2","3","4"}},
                {{"1","2","3","Строка"}, {"1","2","3","4"}, {"1","2","3","4"}, {"1","2","3","4"}}};

        for(int i = 0; i < arrays.length; i++) {
            System.out.printf("Массив №%d:\n", (i + 1));
            printArrayData(arrays[i]);
            System.out.println();
            try {
                System.out.printf("Сумма элементов массива равна: %d\n\n", sumArrayData(arrays[i]));

            } catch (MyArraySizeException | MyArrayDataException e) {
                e.printStackTrace(System.out); // чтобы исключение выводилось сразу после соответствующего массива
                System.out.println();
            }
        }
    }


    public static int sumArrayData(String[][] arrayOfStrings) throws MyArraySizeException, MyArrayDataException {
        int i, sumOfArrayElements = 0;
        if(arrayOfStrings.length == ARRAY_ROWS_COUNT) {
            for (i = 0; i < arrayOfStrings.length; i++) {
                if (arrayOfStrings[i].length != ARRAY_COLUMNS_COUNT) {
                    String exceptionMessage = String.format("Подмассив под индексом %d имеет размер %d, размер подмассивов должен быть равен %d!",
                            i, arrayOfStrings[i].length, ARRAY_COLUMNS_COUNT);
                    throw new MyArraySizeException(exceptionMessage);
                }
            }
        } else {
            String exceptionMessage = String.format("Получен массив с количеством строк %d, а строк должно быть %d!",
                    arrayOfStrings.length, ARRAY_ROWS_COUNT);
            throw new MyArraySizeException(exceptionMessage);
        }

        int j = 0;
        try{
            // т.к. размеры массивов проверены, то используем константы для экономии ресурсов
            for(i = 0; i < ARRAY_ROWS_COUNT; i++)
                for (j = 0; j < ARRAY_COLUMNS_COUNT; j++)
                    sumOfArrayElements += parseInt(arrayOfStrings[i][j]);

        } catch (NumberFormatException e){
            String exceptionMessage = String.format("Значение элемента массива [%d, %d]: '%s' не может быть преобразовано в число!",
                    i, j, arrayOfStrings[i][j]);
            throw new MyArrayDataException(exceptionMessage);
        }

        return sumOfArrayElements;
    }

    public static void printArrayData(String[][] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++)
                System.out.printf("%s\t", array[i][j]);
            System.out.println();
        }
    }

}
