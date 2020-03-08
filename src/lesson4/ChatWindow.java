package lesson4;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow {
    private JList<String> contactField;
    private JList<String> chatField;
    private JTextField messageField;
    private JButton sendButton;
    private JPanel mainPanel;

    public ChatWindow() {

        DefaultListModel<String> contactListModel = new DefaultListModel<>();
        contactField.setModel(contactListModel);

        for (int i = 1; i < 6; i++) contactListModel.addElement("Speaker " + i);

        DefaultListModel<String> chatListModel = new DefaultListModel<>();
        chatField.setModel(chatListModel);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageToChat(chatListModel);
            }
        });

        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageToChat(chatListModel);
            }
        });
    }

    private void sendMessageToChat(DefaultListModel<String> chatListModel) {
        // пока для упрощения в элементе установлен единичный выбор
        String selectedSpeaker = contactField.getSelectedValue();
        if (selectedSpeaker != null) {
            String textFromField = messageField.getText();
            if (!textFromField.isBlank()) {
                chatListModel.addElement(String.format("To \'%s\': %s", selectedSpeaker, textFromField));
                messageField.setText("");
                chatListModel.addElement(String.format("From \'%s\': accepted!", selectedSpeaker));
            }
        } else JOptionPane.showMessageDialog(null, "Select the Speaker first.");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat");
        frame.setContentPane(new ChatWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit from chat");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        menu.add(exitMenuItem);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setVisible(true);
    }

}
