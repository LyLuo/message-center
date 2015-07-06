package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.concurrent.ConcurrentHashMap;

public class Unsigned16 extends DefaultData<Integer> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_2;

	private static final ConcurrentHashMap<Short, Unsigned16> HOLDER = new ConcurrentHashMap<Short, Unsigned16>();

	public static final Unsigned16 UNSIGNED16_0 = get(0);

	public Unsigned16() {
		this(0);
	}

	public Unsigned16(Integer data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		data = buffer.readUnsignedShort();
	}

	public ByteBuf toBinary() {
		return Unpooled.copyShort(data);
	}

	public static Unsigned16 get(Integer i) {
		if (i < 0)
			throw new IllegalArgumentException("Unsigned16 get() parameter "
					+ i + " must > 0");
		if (i >= 0 && i <= 256) {
			if (!HOLDER.containsKey(i)) {
				HOLDER.put(i.shortValue(), new Unsigned16(i));
			}
			return HOLDER.get(i.shortValue());
		}
		return new Unsigned16(i);
	}

}
