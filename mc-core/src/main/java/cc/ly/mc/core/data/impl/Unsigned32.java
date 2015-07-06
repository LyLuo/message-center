package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.concurrent.ConcurrentHashMap;

public class Unsigned32 extends DefaultData<Long> {
	private static final ConcurrentHashMap<Integer, Unsigned32> HOLDER = new ConcurrentHashMap<Integer, Unsigned32>();
	public static final Unsigned32 UNSIGNED32_0 = get(0L);
	public static final Unsigned32 UNSIGNED32_1 = get(1L);
	public static final Unsigned32 UNSIGNED32_2 = get(2L);
	public static final Unsigned32 UNSIGNED32_3 = get(3L);
	public static final Unsigned32 UNSIGNED32_4 = get(4L);
	public static final Unsigned32 UNSIGNED32_5 = get(5L);
	public static final Unsigned32 UNSIGNED32_6 = get(6L);
	public static final Unsigned32 UNSIGNED32_7 = get(7L);
	public static final Unsigned32 UNSIGNED32_8 = get(8L);
	public static final Unsigned32 UNSIGNED32_9 = get(9L);
	public static final Unsigned32 UNSIGNED32_10 = get(10L);
	public static final Unsigned32 UNSIGNED32_12 = get(12L);
	public static final Unsigned32 UNSIGNED32_14 = get(14L);
	public static final Unsigned32 UNSIGNED32_24 = get(24L);
	public static final Unsigned32 UNSIGNED32_32 = get(32L);
	public static final Unsigned32 LENGTH = UNSIGNED32_4;

	public Unsigned32() {
		this(0L);
	}

	public Unsigned32(Long data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		data = buffer.readUnsignedInt();
	}

	public ByteBuf toBinary() {
		return Unpooled.copyInt(data.intValue());
	}

	public static Unsigned32 get(Long l) {
		if (l < 0)
			throw new IllegalArgumentException("Unsigned32 get() parameter "
					+ l + " must > 0");
		if (l >= 0 && l <= 256) {
			if (!HOLDER.containsKey(l.intValue())) {
				HOLDER.put(l.intValue(), new Unsigned32(l));
			}
			return HOLDER.get(l.intValue());
		}
		return new Unsigned32(l);
	}

	public static Unsigned32 add(Unsigned32 a, Unsigned32 b) {
		return Unsigned32.get(a.get() + b.get());
	}

	public static Unsigned32 sub(Unsigned32 a, Unsigned32 b) {
		return Unsigned32.get(a.get() - b.get());
	}

	public static Unsigned32 add(Unsigned32... args) {
		Unsigned32 sum = UNSIGNED32_0;
		for (Unsigned32 u : args) {
			sum = add(sum, u);
		}
		return sum;
	}
}
