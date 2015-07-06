package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.concurrent.ConcurrentHashMap;

public class Integer64 extends DefaultData<Long> {

	private static final ConcurrentHashMap<Long, Integer64> HOLDER = new ConcurrentHashMap<Long, Integer64>();

	public static final Integer64 Integer64_0 = get(0L);

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_8;

	public Integer64() {
		this(0L);
	}

	public Integer64(Long data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		data = buffer.readLong();
	}

	public ByteBuf toBinary() {
		return Unpooled.copyLong(data);
	}

	public static Integer64 get(Long l) {
		if (l >= 0 && l <= 256) {
			if (!HOLDER.containsKey(l)) {
				HOLDER.put(l, new Integer64(l));
			}
			return HOLDER.get(l);
		}
		return new Integer64(l);
	}

}
