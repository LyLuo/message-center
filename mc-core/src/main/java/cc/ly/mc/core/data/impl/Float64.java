package cc.ly.mc.core.data.impl;

import static cc.ly.mc.core.data.impl.Unsigned32.UNSIGNED32_8;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Float64 extends DefaultData<Double> {

	public static final Unsigned32 LENGTH = UNSIGNED32_8;

	public Float64() {
		this(0.0);
	}

	public Float64(Double data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		data = buffer.readDouble();
	}

	public ByteBuf toBinary() {
		return Unpooled.copyDouble(data);
	}
}
