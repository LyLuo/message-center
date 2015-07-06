package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class IgnoredData extends DefaultData<byte[]> {

	public IgnoredData() {
		this(new byte[] {});
	}

	public IgnoredData(byte[] data) {
		super(data);
		length = Unsigned32.get(new Long(data.length));
	}

	public void fromBinary(ByteBuf buffer) {
		data = new byte[length.get().intValue()];
		buffer.readBytes(data);
	}

	public ByteBuf toBinary() {
		return Unpooled.copiedBuffer(data);
	}

}
