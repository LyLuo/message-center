package cc.ly.mc.core.data;

import cc.ly.mc.core.io.Binary;

/**
 * 消息状态
 * 
 * @author ly
 * 
 */
public interface Flag extends Binary {

	/**
	 * @return 是否是群组消息
	 */
	boolean isGroup();

	/**
	 * @return 是否是请求
	 */
	boolean isRequest();

	/**
	 * @return 是否是代理
	 */
	boolean isProxiable();

	/**
	 * @return 是否是错误消息
	 */
	boolean isError();
}
