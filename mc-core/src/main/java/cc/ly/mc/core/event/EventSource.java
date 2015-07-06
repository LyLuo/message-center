package cc.ly.mc.core.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventSource {
	private List<EventListener> listeners;
	
	public EventSource(){
		listeners = new CopyOnWriteArrayList<>();
	}
	
	public void register(EventListener listener){
		listeners.add(listener);
	}
	
	public boolean deregister(EventListener listener){
		return listeners.remove(listener);
	}
	
	public void notifyListeners(Object obj){
		for(EventListener listener : listeners){
			listener.update(this, obj);
		}
	}
}
