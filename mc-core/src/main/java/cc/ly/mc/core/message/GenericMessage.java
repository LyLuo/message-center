package cc.ly.mc.core.message;

import static cc.ly.mc.core.data.impl.Unsigned8.UNSIGNED8_0;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.data.Data;
import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.event.EventManager;
import cc.ly.mc.core.data.impl.Unsigned32;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.data.impl.Unsigned8;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericMessage implements Message {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericMessage.class);

	private static final long serialVersionUID = 523004589062821245L;

	public static final Unsigned8 VERSION = UNSIGNED8_0;
	public static final Unsigned32 HEADER_LENGTH = Unsigned32.UNSIGNED32_24;

	protected Unsigned8 version;

	protected Unsigned16 code;

	protected FlagData flag;

	protected Unsigned32 length;

	protected Integer64 hopByHop;

	protected Integer64 endToEnd;

	protected ChannelHandlerContext context;
	
	protected ConcurrentHashMap<Object, Object> values = new ConcurrentHashMap<Object, Object>();

	protected Map<Unsigned16, Attribute<? extends Data<?>>> attributes = new LinkedHashMap<>();

	protected boolean arriveAtEndPoint;

	public GenericMessage() {
		version = VERSION;
		length = HEADER_LENGTH;
	}

	@Override
	public Unsigned8 version() {
		return version;
	}

	@Override
	public Unsigned32 length() {
		return length;
	}

	@Override
	public void length(Unsigned32 length) {
		this.length = length;
	}

	@Override
	public FlagData flag() {
		return flag;
	}

	@Override
	public void flag(FlagData flag) {
		this.flag = flag;
	}

	@Override
	public Unsigned16 code() {
		return code;
	}
	
	@Override
	public void code(Unsigned16 code) {
		this.code = code;
	}

	@Override
	public Integer64 hopByHop() {
		return hopByHop;
	}

	@Override
	public void hopByHop(Integer64 hopByHop) {
		this.hopByHop = hopByHop;
	}

	@Override
	public Integer64 endToEnd() {
		return endToEnd;
	}

	@Override
	public void endToEnd(Integer64 endToEnd) {
		this.endToEnd = endToEnd;
	}

	@Override
	public void context(ChannelHandlerContext context) {
		this.context = context;
	}

	@Override
	public ChannelHandlerContext context() {
		return context;
	}

	public Attribute<? extends Data<?>> attribute(Unsigned16 key) {
		return attributes.get(key);
	}

	@Override
	public boolean hasAttribute(Unsigned16... keys) {
		if(keys == null){
			return true;
		}
		for (Unsigned16 key : keys) {
			if(!attributes.containsKey(key)){
				return false;
			}
		}
		return true;
	}

	public Map<Unsigned16, Attribute<? extends Data<?>>> attributes() {
		return attributes;
	}

	public void addAttribute(Attribute<? extends Data<?>> attribute) {
		addAttributeWithoutLengthIncreasing(attribute);
		this.length = Unsigned32.add(length, DefaultAttribute.HEADER_LENGTH, attribute.length());
	}

	public void addAttributeWithoutLengthIncreasing(
			Attribute<? extends Data<?>> attribute) {
		attributes.put(attribute.code(), attribute);
	}
	
	public void object(Object key, Object value){
		values.put(key, value);
	}
	
	public Object object(Object key){
		return values.get(key);
	}
	
	@Override
	public void fromBinary(ByteBuf buffer) {
		MessageFactory.getInstance().generateMessage(this, buffer);
	}

	@Override
	public ByteBuf toBinary() {
		return MessageFactory.getInstance().generateByteBuf(this);
	}
	
	@Override
	public void onTimeout(){
		EventManager.getInstance().notifyListeners(this);
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		int len = in.readInt();
		byte[] payload = new byte[len];
		in.read(payload);
		fromBinary(Unpooled.copiedBuffer(payload, 3, len - 3));
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		byte[] payload = toBinary().array();
		out.writeInt(payload.length);
		out.write(payload);
	}

	@Override
	public void onReceived() {
		LOGGER.debug("received a {}", this);
		EventManager.getInstance().notifyListeners(this);
	}

	@Override
	public void arriveAtEndPoint(boolean arriveAtEndPoint){
		this.arriveAtEndPoint = arriveAtEndPoint;
	}

	@Override
	public boolean arriveAtEndPoint(){
		return arriveAtEndPoint;
	}

	@Override
	public boolean valid(){
		return true;
	}
}
