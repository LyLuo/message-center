package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.Flag;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 消息状态工具类
 * 
 * @author ly
 * 
 */
public enum FlagImpl implements Flag {
	REQUEST(false, true, false, false), GROUP_REQUEST(true, true, false, false), PROXY_REQUEST(
			false, true, true, false), PROXY_GROUP_REQUEST(true, true, true,
			false), ANSWER(false, false, false, false), GROUP_ANSWER(true,
			false, false, false), PROXY_ANSWER(false, false, true, false), PROXY_GROUP_ANSWER(
			true, false, true, false), ERROR(false, false, false, true), GROUP_ERROR(
			true, false, false, true), PROXY_GROUP_ERROR(true, false, true,
			true), DUMMY(true, true, true, true);

	boolean group;
	boolean request;
	boolean proxiable;
	boolean error;

	private static final short GROUP_MASK = 0b10000000;
	private static final byte REQUEST_MASK = 0b01000000;
	private static final byte PROXY_MASK = 0b00100000;
	private static final byte ERROR_MASK = 0b00010000;

	private FlagImpl(boolean group, boolean request, boolean proxiable,
			boolean error) {
		this.request = request;
		this.group = group;
		this.proxiable = proxiable;
		this.error = error;
	}

	@Override
	public boolean isGroup() {
		return group;
	}

	@Override
	public boolean isRequest() {
		return request;
	}

	@Override
	public boolean isProxiable() {
		return proxiable;
	}

	@Override
	public boolean isError() {
		return error;
	}

	@Override
	public void fromBinary(ByteBuf buffer) {
		byte payload = buffer.readByte();
		group = (payload & GROUP_MASK) == 0b10000000;
		request = (payload & REQUEST_MASK) == 0b01000000;
		proxiable = (payload & PROXY_MASK) == 0b00100000;
		error = (payload & ERROR_MASK) == 0b00010000;
	}

	@Override
	public ByteBuf toBinary() {
		byte payload = (byte) 0b00000000;
		if (group) {
			payload = (byte) (payload ^ GROUP_MASK);
		}
		if (request) {
			payload = (byte) (payload ^ REQUEST_MASK);
		}
		if (proxiable) {
			payload = (byte) (payload ^ PROXY_MASK);
		}
		if (error) {
			payload = (byte) (payload ^ ERROR_MASK);
		}
		return Unpooled.copiedBuffer(new byte[] { payload });
	}

}
