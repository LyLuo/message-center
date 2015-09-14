package cc.ly.mc.netty.server.message;

import cc.ly.mc.core.message.DefaultMessage;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.core.message.MessageFlag;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ly on 9/13/15.
 */
public class MessageFactory {

    public static final int HEARTBEAT_CODE = 0;
    public static final int REGISTER_CODE = 1;
    public static final int DEREGISTER_CODE = 2;
    public static final int TEXT_CODE = 3;

    public static Message createHeartbeat() {
        Message message = create();
        message.code(HEARTBEAT_CODE);
        message.flag(MessageFlag.REQUEST);
        return message;
    }

    private static Message create() {
        Message message = new DefaultMessage();
        message.version((byte) 1);
        message.hopByHop(IdGenerator.nextHopByHop());
        message.endToEnd(IdGenerator.nextEndToEnd());
        return message;
    }

    private enum IdGenerator {
        HOP_BY_HOP, END_TO_END;
        private AtomicInteger value = new AtomicInteger(1);

        public static int nextHopByHop() {
            return HOP_BY_HOP.value.getAndIncrement();
        }

        public static int nextEndToEnd() {
            return END_TO_END.value.getAndIncrement();
        }
    }

}
