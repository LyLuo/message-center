package cc.ly.mc.client.gui;

import cc.ly.mc.client.event.HeartbeatMessageObserver;
import cc.ly.mc.client.event.RegisterMessageObserver;
import cc.ly.mc.client.netty.SocketClient;
import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.core.event.EventBus;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ly on 9/15/15.
 */
public class App extends JFrame {

    private SocketClient socketClient;

    private String userId;

    private String userName;

    public App() {
        socketClient = new SocketClient("127.0.0.1", 9090);
        this.setSize(240, 135);
        center();
        this.setTitle("Welcome to CHAT");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(new LoginPanel(this));
        this.setVisible(true);
        this.setResizable(false);
        EventBus.getInstance().register(Constant.HEARTBEAT_EVENT, new HeartbeatMessageObserver(this));
        EventBus.getInstance().register(Constant.REGISTER_EVENT, new RegisterMessageObserver(this));
    }

    public void center(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - this.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - this.getHeight()) / 2;
        setLocation(x, y);
    }

    public String userId(){
        return this.userId;
    }

    public void userId(String userId){
        this.userId = userId;
    }

    public String userName(){
        return this.userName;
    }

    public void userName(String userName){
        this.userName = userName;
    }

    public SocketClient socketClient(){
       return this.socketClient;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }

}
