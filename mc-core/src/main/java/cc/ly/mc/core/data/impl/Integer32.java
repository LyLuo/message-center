package cc.ly.mc.core.data.impl;

import static cc.ly.mc.core.data.impl.Unsigned32.UNSIGNED32_4;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.concurrent.ConcurrentHashMap;

public class Integer32 extends DefaultData<Integer> {

	public static final Unsigned32 LENGTH = UNSIGNED32_4;

	private static final ConcurrentHashMap<Integer, Integer32> HOLDER = new ConcurrentHashMap<Integer, Integer32>();

	public static final Integer32 INTEGER32_0 = get(0);
	
	public Integer32() {
		this(0);
	}

	public Integer32(Integer data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		data = buffer.readInt();
	}

	public ByteBuf toBinary() {
		return Unpooled.copyInt(data);
	}

	public static Integer32 get(Integer i) {
		if (i >= -128 && i <= 127) {
			if (!HOLDER.containsKey(i)) {
				HOLDER.put(i, new Integer32(i));
			}
			return HOLDER.get(i);
		}
		return new Integer32(i);
	}
}
