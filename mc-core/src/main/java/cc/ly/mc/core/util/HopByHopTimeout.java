package cc.ly.mc.core.util;

import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.event.EventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2015/6/21.
 */
public class HopByHopTimeout implements Timeout{
    private static final Logger LOGGER = LoggerFactory.getLogger(HopByHopTimeout.class);

    private Integer64 hopByHop;

    public HopByHopTimeout(Integer64 hopByHop){
        this.hopByHop = hopByHop;
    }

    public Integer64 hopByHop() {
        return hopByHop;
    }

    @Override
    public void onTimeout() {
        LOGGER.info("{} hop by hop timeout happened.", this);
        EventManager.getInstance().notifyListeners(HopByHopTimeout.class, this);
    }
}
