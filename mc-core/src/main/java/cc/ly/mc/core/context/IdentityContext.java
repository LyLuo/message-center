package cc.ly.mc.core.context;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.data.impl.Integer32;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.core.message.RelayMessage;
import io.netty.channel.ChannelId;

import java.util.concurrent.ConcurrentHashMap;

import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.data.impl.Unsigned16;

public enum IdentityContext {

	INSTANCE;

	private final ConcurrentHashMap<Integer, Identity> IDENTITIES = new ConcurrentHashMap<>();

	private final ConcurrentHashMap<ChannelId, Integer> CHANNEL_ID_TO_ID = new ConcurrentHashMap<>();

	IdentityContext() {
	}

	public void forward(Message message) {
		Integer to = ((Integer32) message.attribute(Attributes.RECEIVER_ID.getCode()).data()) .get();
		Identity identity = get(to);
		if(identity != null){
			identity.write(message);
		}
	}

	public boolean add(Identity identity) {
		if (IDENTITIES.putIfAbsent(identity.id(), identity) == null) {
			CHANNEL_ID_TO_ID.put(identity.context().channel().id(), identity.id());
			return true;
		} else {
			return false;
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
