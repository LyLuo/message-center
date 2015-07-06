package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.concurrent.ConcurrentHashMap;

public class Unsigned8 extends DefaultData<Short> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_1;

	private static final ConcurrentHashMap<Byte, Unsigned8> HOLDER = new ConcurrentHashMap<Byte, Unsigned8>();

	public static final Unsigned8 UNSIGNED8_0 = get((short) 0);

	public Unsigned8() {
		this((short) 0);
	}

	public Unsigned8(Short data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		data = buffer.readUnsignedByte();
	}

	public ByteBuf toBinary() {
		return Unpooled.copiedBuffer(new byte[] { data.byteValue() });
	}

	public static Unsigned8 get(Short s) {
		if (s < 0)
			throw new IllegalArgumentException("Unsigned8 get() parameter " + s
					+ " must > 0");
		if (s >= 0 && s <= 256) {
			if (!HOLDER.containsKey(s.byteValue())) {
				HOLDER.put(s.byteValue(), new Unsigned8(s));
			}
			return HOLDER.get(s.byteValue());
		}
		return new Unsigned8(s);
	}
}
