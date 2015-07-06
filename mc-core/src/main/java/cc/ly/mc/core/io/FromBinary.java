package cc.ly.mc.core.io;

import io.netty.buffer.ByteBuf;

public interface FromBinary{
	/**
	 * 通过二进制解析填充对象属性
	 * 
	 * @param buffer
	 *            二进制数据
	 * @param length
	 *            消息长度
	 */
	void fromBinary(ByteBuf buffer);
}
