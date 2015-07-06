package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.data.Data;
import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.io.Binary;
import cc.ly.mc.core.util.Timeout;
import io.netty.channel.ChannelHandlerContext;

import java.io.Externalizable;
import java.util.Map;

import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.data.impl.Unsigned8;

/**
 * 消息基本接口
 * 
 * @author ly
 * 
 */
public interface Message extends Binary, Externalizable, Handler, Timeout {
	/**
	 * @return 消息版本号
	 */
	Unsigned8 version();

	/**
	 * @return 消息长度 32位无符号
	 */
	Unsigned32 length();

	/**
	 * @param length
	 *            消息长度 24位无符号
	 */
	void length(Unsigned32 length);

	/**
	 * @return 消息状态
	 */
	FlagData flag();

	/**
	 * @param flag
	 *            消息状态
	 */
	void flag(FlagData flag);

	/**
	 * @return 消息编码
	 */
	Unsigned16 code();

	/**
	 * @param code
	 *            消息编码
	 */
	void code(Unsigned16 code);

	/**
	 * @return HopByHop标识
	 */
	Integer64 hopByHop();

	/**
	 * 
	 * @param hopByHop 标识
	 */
	void hopByHop(Integer64 hopByHop);

	/**
	 * @return EndToEnd标识
	 */
	Integer64 endToEnd();

	/**
	 * @param endToEnd 标识
	 */
	void endToEnd(Integer64 endToEnd);

	/**
	 * @param context
	 *            Netty context
	 */
	void context(ChannelHandlerContext context);

	/**
	 * @return ctx Netty context
	 */
	ChannelHandlerContext context();

	/**
	 * @param key 属性code
	 * @return
	 */
	Attribute<? extends Data<?>> attribute(Unsigned16 key);

	/**
	 *
	 * @param keys 属性code
	 * @return
	 */
	boolean hasAttribute(Unsigned16... keys);

	/**
	 * @return all attributes
	 */
	Map<Unsigned16, Attribute<? extends Data<?>>> attributes();

	/**
	 * @param attribute
	 *            属性值
	 */
	void addAttribute(Attribute<? extends Data<?>> attribute);

	/**
	 * @param attribute
	 *            属性值
	 */
	void addAttributeWithoutLengthIncreasing(
			Attribute<? extends Data<?>> attribute);
	
	/**
	 * 设置相关的属性
	 * @param key
	 * @param value
	 */
	void object(Object key,Object value);
	
	/**
	 * 返回设置的属性
	 * @param key
	 * @return 对应的值
	 */
	Object object(Object key);

	/**
	 *
	 * @param arriveAtEndPoint 是否到达终点
	 */
	void arriveAtEndPoint(boolean arriveAtEndPoint);

	/**
	 * 是否到达终点
	 * @return
	 */
	boolean arriveAtEndPoint();

	/**
	 * 消息格式是否合法
	 * @return
	 */
	boolean valid();
}
