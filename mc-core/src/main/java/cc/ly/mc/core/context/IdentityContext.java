package cc.ly.mc.core.context;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.data.impl.Integer32;
import cc.ly.mc.core.message.Message;
import io.netty.channel.ChannelId;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public enum IdentityContext {

	INSTANCE;

	private final ConcurrentHashMap<Integer, Identity> IDENTITIES = new ConcurrentHashMap<>();

	private final ConcurrentHashMap<ChannelId, Integer> CHANNEL_ID_TO_ID = new ConcurrentHashMap<>();

	private final ConcurrentHashMap<Integer, LinkedList<Message>> DELAY_MESSAGES = new ConcurrentHashMap<>();

	IdentityContext() {
	}

	public void forward(Message message) {
		Integer to = ((Integer32) message.attribute(Attributes.RECEIVER_ID.getCode()).data()) .get();
		Identity identity = get(to);
		if(identity != null){
			identity.write(message);
		}else{
			synchronized (this) {
				if (!DELAY_MESSAGES.containsKey(to)) {
					DELAY_MESSAGES.put(to, new LinkedList<Message>());
				}
				DELAY_MESSAGES.get(to).add(message);
			}
		}
	}

	public void add(Identity identity) {
		Identity old = IDENTITIES.put(identity.id(), identity);
		if(old != null){
			old.context().close();
		}
		CHANNEL_ID_TO_ID.put(identity.context().channel().id(), identity.id());
		synchronized (this){
			if(DELAY_MESSAGES.containsKey(identity.id()) && DELAY_MESSAGES.get(identity.id()).size() > 0){
				LinkedList<Message> messages = DELAY_MESSAGES.get(identity.id());
				Iterator<Message> it = messages.iterator();
				while (it.hasNext()){
					identity.write(it.next());
					it.remove();
				}
			}
		}
	}

	public void remove(Integer id) {
		if (id != null) {
			IDENTITIES.remove(id);
		}
	}

	public void remove(ChannelId channelId) {
		remove(CHANNEL_ID_TO_ID.remove(channelId));
	}
	
	public Identity get(ChannelId channelId){
		Integer id = CHANNEL_ID_TO_ID.get(channelId);
		return IDENTITIES.get(id);
	}

	public Identity get(Integer id) {
		return IDENTITIES.get(id);
	}

	public Identity getServer(){
		return IDENTITIES.get(Identity.SERVER_ID);
	}

}
