package cc.ly.mc.core.attribute;

import cc.ly.mc.core.data.Data;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.data.impl.Unsigned32;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public abstract class DefaultAttribute<T extends Data<?>> implements
		Attribute<T> {

	public static final Unsigned32 HEADER_LENGTH = Unsigned32.UNSIGNED32_6;
	
	protected Unsigned32 length;

	protected T data;

	protected Unsigned16 code;

	protected Class<T> clazz;

	public DefaultAttribute(T data, Class<T> clazz) {
		this.data = data;
		this.length = this.data.length();
		this.clazz = clazz;
		this.code = Unsigned16.UNSIGNED16_0;
	}

	@Override
	public T data() {
		return data;
	}

	@Override
	public void data(T data) {
		this.data = data;
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
	public Unsigned32 length() {
		return length;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		try {
			data = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		length = new Unsigned32();
		length.fromBinary(buffer);
		data.length(length);
		data.fromBinary(buffer);
	}

	@Override
	public ByteBuf toBinary() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeBytes(code.toBinary());
		buffer.writeBytes(length.toBinary());
		buffer.writeBytes(data.toBinary());
		return buffer;
	}
}
