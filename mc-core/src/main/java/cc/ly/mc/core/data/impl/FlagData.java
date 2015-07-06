package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.DefaultData;
import cc.ly.mc.core.data.Flag;
import io.netty.buffer.ByteBuf;

public class FlagData extends DefaultData<Flag> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_1;

	public FlagData() {
		this(FlagImpl.DUMMY);
	}

	public FlagData(Flag data) {
		super(data);
		this.length = LENGTH;
	}

	@Override
	public Unsigned32 length() {
		return length;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		data.fromBinary(buffer);
	}

	@Override
	public ByteBuf toBinary() {
		return data.toBinary();
	}
}
