package cc.ly.mc.client;

import cc.ly.mc.client.netty.SocketClient;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ly on 9/15/15.
 */
public class App extends JFrame {

    private SocketClient socketClient;

    public App() {
        socketClient = new SocketClient("localhost", 9090);
        this.setSize(240, 135);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - this.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - this.getHeight()) / 2;
        setLocation(x, y);
        this.setTitle("Welcome to CHAT");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(new LoginPanel(this));
        this.setVisible(true);
        this.setResizable(false);
    }

    public SocketClient socketClient(){
       return this.socketClient;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }

}
