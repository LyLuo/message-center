package cc.ly.mc.client;

import cc.ly.mc.client.message.MessageFactory;

import javax.swing.*;

/**
 * Created by ly on 9/15/15.
 */
public class LoginPanel extends JPanel{

    private JTextField id = new JTextField(16);

    private JTextField name = new JTextField(16);

    private JButton login = new JButton("login");

    private JButton reset = new JButton("reset");

    private final App app;

    public LoginPanel(App app){
        this.app = app;
        this.add(id);
        this.add(name);
        this.add(login);
        this.add(reset);
        login.addActionListener(event -> {
            app.socketClient().start();
            while (!app.socketClient().ready()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            if (!"".equals(id.getText()) && !"".equals(name.getText())) {
                app.socketClient().write(MessageFactory.createRegister(id.getText(), name.getText()));
            }
        });
        reset.addActionListener(e -> {
            id.setText("");
            name.setText("");
        });
    }
}
