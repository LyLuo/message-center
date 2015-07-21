package cc.ly.mc.core.context;

import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.message.*;
import cc.ly.mc.core.util.EndToEndTimeout;
import cc.ly.mc.core.util.HopByHopTimeout;
import cc.ly.mc.core.util.Timeout;
import cc.ly.mc.core.util.TimeoutManager;
import io.netty.channel.ChannelId;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2015/6/19.
 */
public enum MessageContext {
    INSTANCE;

    private final ConcurrentHashMap<ChannelId, ConcurrentHashMap<Integer64, Message>> repository;

    private TimeoutManager<Timeout> timeoutManager;

    private AtomicLong hopByHopBase = new AtomicLong(1);

    private AtomicLong endToEndBase = new AtomicLong(1);

    MessageContext() {
        repository = new ConcurrentHashMap<>();
        timeoutManager = new TimeoutManager<>(10000, 1000);
    }

    public void register(ChannelId id) {
        repository.put(id, new ConcurrentHashMap<Integer64, Message>());
    }

    public void register(Message message) {
        ConcurrentHashMap<Integer64, Message> messages = repository.get(message.context().channel().id());
        if (messages != null) {
            messages.put(message.endToEnd(), message);
        }
    }

    public void registerAcmTimeout(Message message) {
        ConcurrentHashMap<Integer64, Message> messages = repository.get(message.context().channel().id());
        if (messages != null) {
            if (!message.arriveAtEndPoint()) {
                TimeoutManager.Slot endToEndSlot = timeoutManager.register(new EndToEndTimeout(message.endToEnd()));
                message.object(EndToEndTimeout.class, endToEndSlot);
                if (message instanceof RelayMessage) {
                    TimeoutManager.Slot hopByHopSlot = timeoutManager.register(new HopByHopTimeout(message.hopByHop()));
                    message.object(HopByHopTimeout.class, hopByHopSlot);
                }
            }
        }
    }

    public void deregisterAckTimeout(Message message) {
        ConcurrentHashMap<Integer64, Message> messages = repository.get(message.context().channel().id());
        if (messages != null) {
            if (message instanceof HopByHopAckMessage) {
                timeoutManager.deregister((TimeoutManager.Slot) messages.get(message.endToEnd()).object(HopByHopTimeout.class));
            } else {
                timeoutManager.deregister((TimeoutManager.Slot) messages.get(message.endToEnd()).object(EndToEndTimeout.class));
            }
        }
    }

    public void deregister(Message message) {
        ConcurrentHashMap<Integer64, Message> messages = repository.get(message.context().channel().id());
        if (messages != null) {
            messages.remove(message.endToEnd());
        }
    }

    public void deregister(Identity id) {
        repository.remove(id);
    }

    public Integer64 generateHopByHop() {
        return Integer64.get(hopByHopBase.incrementAndGet());
    }

    public Integer64 generateEndToEnd() {
        return Integer64.get(endToEndBase.incrementAndGet());
    }
}
