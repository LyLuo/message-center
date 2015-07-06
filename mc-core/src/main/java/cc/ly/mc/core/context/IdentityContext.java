package cc.ly.mc.core.context;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.core.message.RelayMessage;
import io.netty.channel.ChannelId;

import java.util.concurrent.ConcurrentHashMap;

import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.data.impl.Unsigned16;

public enum IdentityContext {

	INSTANCE;

	private final ConcurrentHashMap<Long, Identity> IDENTITIES = new ConcurrentHashMap<>();

	private final ConcurrentHashMap<ChannelId, Long> CHANNEL_ID_TO_IDENTITY = new ConcurrentHashMap<>();

	IdentityContext() {
	}

	public void forward(Message message) {
		Long to = ((Integer64) message.attribute(Attributes.RECEIVER_ID.getCode()).data()) .get();
		Identity identity = get(to);
		if(identity != null){
			identity.write(message);
		}
	}

	public boolean add(Identity identity) {
		if (IDENTITIES.putIfAbsent(identity.id(), identity) == null) {
			CHANNEL_ID_TO_IDENTITY.put(identity.context().channel().id(), identity.id());
			return true;
		} else {
			return false;
		}
	}

	public void remove(Long id) {
		if (id != null) {
			IDENTITIES.remove(id);
		}
	}

	public void remove(ChannelId id) {
		remove(CHANNEL_ID_TO_IDENTITY.remove(id));
	}
	
	public Identity get(ChannelId id){
		return get(CHANNEL_ID_TO_IDENTITY.remove(id));
	}

	public Identity get(Long id) {
		return IDENTITIES.get(id);
	}

	public Identity getServer(){
		return IDENTITIES.get(Identity.SERVER_ID);
	}
}
