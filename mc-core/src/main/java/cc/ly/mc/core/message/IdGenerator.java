package cc.ly.mc.core.message;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ly on 9/15/15.
 */
public enum IdGenerator {
    HOP_BY_HOP, END_TO_END;
    private AtomicInteger value = new AtomicInteger(1);

    public static int nextHopByHop() {
        return HOP_BY_HOP.value.getAndIncrement();
    }

    public static int nextEndToEnd() {
        return END_TO_END.value.getAndIncrement();
    }
}
