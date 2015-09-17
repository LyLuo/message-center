package cc.ly.mc.client;

import cc.ly.mc.client.event.TextMessageObserver;
import cc.ly.mc.client.message.MessageFactory;
import cc.ly.mc.common.netty.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by ly on 9/15/15.
 */
public class MainPanel extends JPanel {

    private JLabel success = new JLabel();

    private DefaultListModel<String> dataModel = new DefaultListModel<>();

    private JList<String> textList = new JList<>(dataModel);

    private JPanel sendTextPanel = new JPanel();

    private JTextField receiverId = new JTextField(16);

    private JTextField text = new JTextField(16);

    private JButton send = new JButton("send");

    private App app;

    public MainPanel(App app, String name) {
        this.app = app;
        this.app.setSize(600,400);
        this.app.center();
        this.setLayout(new BorderLayout());
        success.setText("hello " + name + ", welcome to chat!");
        this.add(success, BorderLayout.NORTH);
        this.add(textList, BorderLayout.CENTER);
        sendTextPanel.add(receiverId);
        sendTextPanel.add(text);
        sendTextPanel.add(send);
        this.add(sendTextPanel, BorderLayout.SOUTH);
        send.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!"".equals(receiverId.getText()) && !"".equals(text.getText())) {
                    app.socketClient().write(MessageFactory.createText(app.id(), receiverId.getText(), text.getText()));
                }
            }
        });
    }

    public App app() {
        return app;
    }

    public void addText(String text) {
        dataModel.addElement(text);
    }
}
