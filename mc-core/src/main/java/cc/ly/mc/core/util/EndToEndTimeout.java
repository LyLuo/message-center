package cc.ly.mc.core.util;

import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.event.EventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2015/6/21.
 */
public class EndToEndTimeout implements Timeout{
    private static final Logger LOGGER = LoggerFactory.getLogger(EndToEndTimeout.class);

    private Integer64 endToEnd;

    public EndToEndTimeout(Integer64 endToEnd){
        this.endToEnd = endToEnd;
    }

    public Integer64 endToEnd() {
        return endToEnd;
    }

    @Override
    public void onTimeout() {
        LOGGER.info("{} end to end timeout happened.", this);
        EventManager.getInstance().notifyListeners(EndToEndTimeout.class, this);
    }
}
