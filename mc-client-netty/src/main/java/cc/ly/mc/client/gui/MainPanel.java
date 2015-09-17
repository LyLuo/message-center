package cc.ly.mc.client.gui;

import cc.ly.mc.client.message.MessageFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by ly on 9/15/15.
 */
public class MainPanel extends JPanel {

    private JLabel success = new JLabel();

    private JList<String> textList = new JList<>(new DefaultListModel<>());

    private JPanel sendTextPanel = new JPanel();

    private JTextField receiverId = new JTextField(16);

    private JTextField text = new JTextField(16);

    private JButton send = new JButton("send");

    private App app;

    public MainPanel(App app) {
        this.app = app;
        this.setLayout(new BorderLayout());
        success.setText("hello " + app.userName() + ", welcome to chat!");
        this.add(success, BorderLayout.NORTH);
        this.add(textList, BorderLayout.CENTER);
        sendTextPanel.add(receiverId);
        sendTextPanel.add(text);
        sendTextPanel.add(send);
        this.add(sendTextPanel, BorderLayout.SOUTH);
        app.setSize(600,400);
        app.center();
        send.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!"".equals(receiverId.getText()) && !"".equals(text.getText())) {
                    app.socketClient().write(MessageFactory.createText(app.userId(), receiverId.getText(), text.getText()));
                }
            }
        });
    }

    public App app() {
        return app;
    }

    public void addText(String text){
        ((DefaultListModel<String>)textList.getModel()).addElement(text);
    }
}
