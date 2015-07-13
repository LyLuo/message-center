package cc.ly.mc.demo.client;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.attribute.impl.Integer32Attribute;
import cc.ly.mc.core.attribute.impl.UTF8Attribute;
import cc.ly.mc.core.client.io.SocketClient;
import cc.ly.mc.core.context.Identity;
import cc.ly.mc.core.context.IdentityContext;
import cc.ly.mc.core.context.MessageContext;
import cc.ly.mc.core.data.impl.*;
import cc.ly.mc.demo.client.listener.TextMessageListener;
import cc.ly.mc.core.event.EventManager;
import cc.ly.mc.core.event.EventSource;
import cc.ly.mc.core.io.ConnectedListener;
import cc.ly.mc.core.util.Timeout;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cc.ly.mc.core.attribute.impl.Integer64Attribute;
import cc.ly.mc.demo.client.listener.EndToEndAckMessageListener;
import cc.ly.mc.demo.client.listener.HopByHopAckMessageListener;
import cc.ly.mc.core.message.RegisterMessage;
import cc.ly.mc.core.message.TextMessage;
import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.message.EndToEndAckMessage;
import cc.ly.mc.core.message.HopByHopAckMessage;
import io.netty.channel.ChannelHandlerContext;

public class Chat extends JFrame {

    private JPanel contentPane;
    private JTextField userNameField;
    private JTextField userIdField;
    private JTextField contentField;
    private SocketClient client;
    private Integer userId;
    private String userName;
    private final DefaultListModel<String> dataModel = new DefaultListModel<String>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Chat frame = new Chat();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Chat() {
        client = new SocketClient("localhost", 9000);
        client.addConnectedListener(new ConnectedListener() {

            @Override
            public void onConnect(ChannelHandlerContext ctx) {
                System.out.println("connect to server " + ctx.channel());
                Identity identity = new Identity(ctx);
                IdentityContext.INSTANCE.add(identity);
                MessageContext.INSTANCE.register(ctx.channel().id());
            }
        });
        EventManager.getInstance().registerListener(TextMessage.class, new TextMessageListener(dataModel));
        EventManager.getInstance().registerListener(HopByHopAckMessage.class, new HopByHopAckMessageListener());
        EventManager.getInstance().registerListener(EndToEndAckMessage.class, new EndToEndAckMessageListener());
        EventManager.getInstance().registerListener(Timeout.class,
                new EventListener() {
                    @Override
                    public void update(EventSource event, Object object) {
                        System.out.println("haha timeout happened");
                    }
                });
        client.start();
        setTitle("Simple Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 556, 408);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("User ID");
        lblNewLabel.setBounds(26, 16, 83, 23);
        contentPane.add(lblNewLabel);

        userIdField = new JTextField();
        userIdField.setBounds(127, 11, 223, 28);
        contentPane.add(userIdField);
        userIdField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("User Name");
        lblNewLabel_1.setBounds(26, 56, 83, 16);
        contentPane.add(lblNewLabel_1);

        userNameField = new JTextField();
        userNameField.setBounds(127, 51, 223, 28);
        contentPane.add(userNameField);
        userNameField.setColumns(10);

        contentField = new JTextField();
        contentField.setBounds(26, 244, 336, 28);
        contentPane.add(contentField);
        contentField.setColumns(10);

        JButton login = new JButton("Login");
        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("login");
                userId = Integer.parseInt(userIdField.getText());
                userName = userNameField.getText();
                RegisterMessage reg = new RegisterMessage();
                reg.hopByHop(MessageContext.INSTANCE.generateHopByHop());
                reg.endToEnd(MessageContext.INSTANCE.generateEndToEnd());
                reg.flag(new FlagData(FlagImpl.REQUEST));
                Integer32Attribute id = new Integer32Attribute(Integer32.get(userId));
                id.code(Attributes.SENDER_ID.getCode());
                reg.addAttribute(id);
                UTF8Attribute name = new UTF8Attribute(new UTF8(userName));
                name.code(Attributes.TOKEN.getCode());
                reg.addAttribute(name);
                IdentityContext.INSTANCE.getServer().write(reg);
            }
        });
        login.setBounds(377, 50, 79, 29);
        contentPane.add(login);

        JButton send = new JButton("send");
        send.setBounds(377, 243, 79, 29);
        contentPane.add(send);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(26, 96, 336, 125);
        contentPane.add(scrollPane);
        JList<String> list = new JList<>(dataModel);
        scrollPane.setViewportView(list);
        send.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String[] strs = contentField.getText().split(":");
                dataModel.addElement("me : " + strs[1]);
                TextMessage txt = new TextMessage();
                txt.hopByHop(MessageContext.INSTANCE.generateHopByHop());
                txt.endToEnd(MessageContext.INSTANCE.generateEndToEnd());
                txt.flag(new FlagData(FlagImpl.REQUEST));
                UTF8Attribute utf8 = new UTF8Attribute(new UTF8(contentField .getText()));
                utf8.code(Unsigned16.get(5));
                txt.addAttribute(utf8);
                Integer64Attribute receiverId = new Integer64Attribute(
                        new Integer64(Long.parseLong(strs[0])));
                receiverId.code(Unsigned16.get(3));
                txt.addAttribute(receiverId);
                Integer32Attribute senderId = new Integer32Attribute(
                        new Integer32(userId));
                senderId.code(Unsigned16.get(1));
                txt.addAttribute(senderId);
                IdentityContext.INSTANCE.getServer().write(txt);
            }
        });
    }

}
