package cc.ly.mc.core.io;

import io.netty.buffer.ByteBuf;

public interface ToBinary{
	/**
	 * @return 对象的二进制格式
	 */
	ByteBuf toBinary();
}
