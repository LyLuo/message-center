package cc.ly.mc.core.data.impl;

import static cc.ly.mc.core.data.impl.Unsigned32.UNSIGNED32_4;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Float32 extends DefaultData<Float> {

	public static final Unsigned32 LENGTH = UNSIGNED32_4;

	public Float32() {
		this(0.0F);
	}

	public Float32(Float data) {
		super(data);
		this.length = LENGTH;
	}

	public Unsigned32 length() {
		return length;
	}

	public void fromBinary(ByteBuf buffer) {
		data = buffer.readFloat();
	}

	public ByteBuf toBinary() {
		return Unpooled.copyFloat(data);
	}

}
