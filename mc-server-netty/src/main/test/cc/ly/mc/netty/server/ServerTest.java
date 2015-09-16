package cc.ly.mc.netty.server;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by ly on 9/14/15.
 */
public class ServerTest {
    @Test
    public void test() throws IOException, InterruptedException {
        Socket socket = new Socket("localhost",9090);
        OutputStream os = socket.getOutputStream();
        byte[] payload = new byte[]{
                0x01,0x00,0x00,0x17,
                (byte) 0x80,0x00,0x00,0x01,
                0x00,0x00,0x00,0x01,
                0x00,0x00,0x00,0x01,
                0x00,0x02,0b00001100,0x00,0x00,0x07,0x41,//string
        };
        os.write(payload);
        os.flush();
        Thread.sleep(10000);
    }
}
