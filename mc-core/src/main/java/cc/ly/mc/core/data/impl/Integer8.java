package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.concurrent.ConcurrentHashMap;

public class Integer8 extends DefaultData<Byte> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_1;

	private static final ConcurrentHashMap<Byte, Integer8> HOLDER = new ConcurrentHashMap<Byte, Integer8>();
	
	public static final Integer8 INTEGER8_0 = get((byte)0);

	public Integer8() {
		this((byte) 0);
	}

	public Integer8(Byte data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		data = buffer.readByte();
	}

	public ByteBuf toBinary() {
		return Unpooled.copiedBuffer(new byte[] { data });
	}

	public static Integer8 get(Byte b) {
		if (b >= -128 && b <= 127) {
			if (!HOLDER.containsKey(b)) {
				HOLDER.put(b, new Integer8(b));
			}
			return HOLDER.get(b);
		}
		return new Integer8(b);
	}
}
