package cc.ly.mc.core.event;

import java.util.HashMap;
import java.util.Map;

import cc.ly.mc.core.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventManager {

	private static final Logger LOGGER = LoggerFactory .getLogger(EventManager.class);

	private static class ManagerHolder {
		private static EventManager INSTANCE = new EventManager();
	}

	public Map<String, EventSource> EVENT_SOURCE = new HashMap<>();

	private EventManager() {
	}

	public static EventManager getInstance() {
		return ManagerHolder.INSTANCE;
	}

	public synchronized void registerListener(String name, EventListener listener) {
		LOGGER.debug("registerAckTimeout a listener {} with {} ", listener, name);
		EventSource eventSource = EVENT_SOURCE.get(name);
		if (eventSource == null) {
			eventSource = new EventSource();
			EVENT_SOURCE.put(name, eventSource);
		}
		eventSource.register(listener);
	}
	public synchronized void registerListener(Class<?> clazz, EventListener listener) {
		registerListener(clazz.getCanonicalName(), listener);
	}

	public synchronized void deregisterListener(String name, EventListener listener) {
		LOGGER.debug("deregisterAckTimeout a listener {} with {} ", listener, name);
		EventSource eventSource = EVENT_SOURCE.get(name);
		if (eventSource != null) {
			eventSource.deregister(listener);
		}
	}

	public synchronized void deregisterListener(Class<?> clazz, EventListener listener) {
		deregisterListener(clazz.getCanonicalName(), listener);
	}
	public synchronized void notifyListeners(Object object) {
		notifyListeners(object.getClass(), object);
	}

	public synchronized void notifyListeners(Class clazz, Object object) {
		notifyListeners(clazz.getCanonicalName(), object);
	}

	public synchronized void notifyListeners(String name, Object object) {
		LOGGER.debug("notify listeners {} happend with {} ", name, object);
		EventSource eventSource = EVENT_SOURCE.get(name);
		if (eventSource != null) {
			eventSource.notifyListeners(object);
		}
	}
}

