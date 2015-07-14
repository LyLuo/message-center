package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.FlagImpl;
import cc.ly.mc.core.event.EventManager;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SystemResponseMessage extends GenericMessage{

    private static final Logger LOGGER = LoggerFactory .getLogger(SystemResponseMessage.class);

    @Override
    public void onReceived() {
        LOGGER.debug("received a {}", this);
        if (arriveAtEndPoint()) {
            EventManager.getInstance().notifyListeners(this);
        } else {
            LOGGER.error("not suppose to receive a system response message");
        }
    }
}
