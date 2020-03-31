package ru.geekbrains.java2.client.command;

import java.io.Serializable;

public class SetNewNicknameCommand implements Serializable{
    private final String oldNickname;
    private final String newNickname;

    public SetNewNicknameCommand(String oldNickname, String newNickname) {
        this.oldNickname = oldNickname;
        this.newNickname = newNickname;
    }

    public String getOldNickname() {
        return oldNickname;
    }

    public String getNewNickname() {
        return newNickname;
    }
}
