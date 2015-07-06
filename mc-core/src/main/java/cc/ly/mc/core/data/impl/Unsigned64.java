package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class Unsigned64 extends DefaultData<BigInteger> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_8;

	private static final ConcurrentHashMap<Long, Unsigned64> HOLDER = new ConcurrentHashMap<Long, Unsigned64>();

	public Unsigned64() {
		this(BigInteger.ZERO);
	}

	public Unsigned64(BigInteger data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		byte[] payload = new byte[LENGTH.get().intValue()];
		buffer.readBytes(payload);
		data = new BigInteger(payload);
	}

	public ByteBuf toBinary() {
		return Unpooled.copyLong(data.longValue());
	}

	public static Unsigned64 get(BigInteger data) {
		if (data.compareTo(BigInteger.ZERO) < 0)
			throw new IllegalArgumentException("Unsigned64 get() parameter "
					+ data.toString() + " must > 0");
		if (data.longValue() >= 0 && data.longValue() <= 256) {
			if (!HOLDER.containsKey(data.longValue())) {
				HOLDER.put(data.longValue(), new Unsigned64(data));
			}
			return HOLDER.get(data.longValue());
		}
		return new Unsigned64(data);
	}

}
