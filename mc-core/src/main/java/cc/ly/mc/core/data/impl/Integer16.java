package cc.ly.mc.core.data.impl;

import static cc.ly.mc.core.data.impl.Unsigned32.UNSIGNED32_2;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.concurrent.ConcurrentHashMap;

public class Integer16 extends DefaultData<Short> {

	public static final Unsigned32 LENGTH = UNSIGNED32_2;

	private static final ConcurrentHashMap<Short, Integer16> HOLDER = new ConcurrentHashMap<Short, Integer16>();

	public static final Integer16 INTEGER16_0 = get((short)0);
	
	public Integer16() {
		this((short) 0);
	}

	public Integer16(Short data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		data = buffer.readShort();
	}

	public ByteBuf toBinary() {
		return Unpooled.copyInt(data);
	}

	public static Integer16 get(Short s) {
		if (s >= -128 && s <= 127) {
			if (!HOLDER.containsKey(s)) {
				HOLDER.put(s, new Integer16(s));
			}
			return HOLDER.get(s);
		}
		return new Integer16(s);
	}
}
