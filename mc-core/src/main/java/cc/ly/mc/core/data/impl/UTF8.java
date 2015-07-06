package cc.ly.mc.core.data.impl;

import static io.netty.util.CharsetUtil.UTF_8;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UTF8 extends DefaultData<String> {

	private static final String EMPTY_STRING = "";

	public UTF8() {
		this(EMPTY_STRING);
	}

	public UTF8(String data) {
		super(data);
		this.length = Unsigned32.get(new Long(data.getBytes(UTF_8).length));
	}

	public void fromBinary(ByteBuf buffer) {
		byte[] payload = new byte[length.get().intValue()];
		buffer.readBytes(payload);
		data = new String(payload, UTF_8);
	}

	public ByteBuf toBinary() {
		byte[] payload = data.getBytes(UTF_8);
		return Unpooled.wrappedBuffer(payload);
	}
	
}