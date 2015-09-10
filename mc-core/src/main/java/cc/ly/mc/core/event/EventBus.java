package cc.ly.mc.core.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 观察者模式，事件管理器
 * Created by ly on 9/10/15.
 */
public class EventBus {
    private static class EventBusHolder {
        private static final EventBus INSTANCE = new EventBus();
    }

    private ExecutorService service = Executors.newCachedThreadPool();

    private Map<String, CopyOnWriteArrayList<EventObserver>> allObservers = new ConcurrentHashMap<>();

    public static EventBus getInstance() {
        return EventBusHolder.INSTANCE;
    }

    public void register(String name, EventObserver observer) {
        CopyOnWriteArrayList<EventObserver> queue = new CopyOnWriteArrayList<>();
        queue.add(observer);
        CopyOnWriteArrayList<EventObserver> observers = allObservers.putIfAbsent(name, queue);
        if (observers != null) {
            observers.add(observer);
        }
    }


    public void deregister(String name, EventObserver observer) {
        if (allObservers.containsKey(name)) {
            allObservers.get(name).remove(observer);
        }
    }

    /**
     * 异步方式执行
     *
     * @param name  事件名
     * @param event 事件
     */
    public void notify(String name, Object event) {
        if (allObservers.containsKey(name)) {
            allObservers.get(name).forEach(observer -> service.execute(() -> observer.update(event)));
        }
    }
}
