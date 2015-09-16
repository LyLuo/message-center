package cc.ly.mc.client;

import javax.swing.*;

/**
 * Created by ly on 9/15/15.
 */
public class MainPanel extends JPanel{

    private JLabel success = new JLabel();

    private App app;

    public MainPanel(App app, String name){
        this.app = app;
        success.setText("hello " + name + ", welcome to chat!");
        this.add(success);
    }
}
