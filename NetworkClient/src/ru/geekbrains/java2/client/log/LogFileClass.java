package ru.geekbrains.java2.client.log;

import java.io.*;
import java.util.List;

public class LogFileClass {
    private static final String FILE_NAME_TEMPLATE = "NetworkClient/%schat_history.txt";

    public static void writeLineToLog(String login, String line){
        try {
            BufferedWriter fileBuffer = new BufferedWriter(new FileWriter(getFileName(login), true));
            fileBuffer.write(String.format("%s%s", line, System.lineSeparator()));
            fileBuffer.flush();
            fileBuffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getLogHistoryLastLines(String login, List<String> historyList, int linesCount){
        try {
            BufferedReader fileBuffer = new BufferedReader(new FileReader(getFileName(login)));
            String line;
            while((line = fileBuffer.readLine()) != null) addLineToList(historyList, linesCount, line);
            fileBuffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addLineToList(List<String> list, int listSize, String line){
        while(list.size() >= listSize) list.remove(0);
        list.add(line);
    }

    private static String getFileName(String login){
        return login.isBlank() ? String.format(FILE_NAME_TEMPLATE, "") : String.format(FILE_NAME_TEMPLATE, login + "_");
    }
}
