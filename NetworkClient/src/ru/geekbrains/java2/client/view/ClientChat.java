package ru.geekbrains.java2.client.view;

import ru.geekbrains.java2.client.controller.ClientController;
import ru.geekbrains.java2.client.db.NetworkChatDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class ClientChat extends JFrame {

    private JPanel mainPanel;
    private JList<String> usersList;
    private JTextField messageTextField;
    private JButton sendButton;
    private JTextArea chatText;

    private ClientController controller;

    public ClientChat(ClientController controller) {
        this.controller = controller;
        setTitle(controller.getUsername());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem changeNicknameMenuItem = new JMenuItem("Change Nickname");
        changeNicknameMenuItem.addActionListener(e -> changeChatNickname());
        menu.add(changeNicknameMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        addListeners();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.shutdown();
            }
        });
    }


    private void addListeners() {
        sendButton.addActionListener(e -> ClientChat.this.sendMessage());
        messageTextField.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        String message = messageTextField.getText().trim();
        if (message.isEmpty()) {
            return;
        }

        appendOwnMessage(message);

        if (usersList.getSelectedIndex() < 1) {
            controller.sendMessageToAllUsers(message);
        }
        else {
            String username = usersList.getSelectedValue();
            controller.sendPrivateMessage(username, message);
        }

        messageTextField.setText(null);
    }

    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatText.append(message);
            chatText.append(System.lineSeparator());
        });
    }


    private void appendOwnMessage(String message) {
        appendMessage("Me: " + message);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, "Failed to send message!");
    }

    public void updateUsers(List<String> users) {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<String> model = new DefaultListModel<>();
            model.addAll(users);
            usersList.setModel(model);
        });
    }

    public void changeChatNickname(){
        String newNickname = "";
        newNickname = JOptionPane.showInputDialog("Input new nickname:").trim();
        if(!newNickname.isEmpty()) {
            try {
                NetworkChatDB.connectToDB();
                String result = NetworkChatDB.changeNickname(controller.getUsername(), newNickname);
                if (result == null){
                    controller.sendSetNewNickname(controller.getUsername(), newNickname);
                    setTitle(newNickname);
                }
                else JOptionPane.showMessageDialog(this, result);
                NetworkChatDB.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else JOptionPane.showMessageDialog(this, "Input string is empty!");
    }
}
