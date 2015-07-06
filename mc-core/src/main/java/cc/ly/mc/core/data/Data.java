package cc.ly.mc.core.data;

import cc.ly.mc.core.io.Binary;
import cc.ly.mc.core.data.impl.Unsigned32;

/**
 * 数据接口
 * 
 * @author ly
 * 
 * @param <T>
 *            T 具体数据类型
 */
public interface Data<T> extends Binary {

	/**
	 * 
	 * @return 具体数据
	 */
	T get();

	/**
	 * 设置值
	 * 
	 * @param data
	 *            具体数据
	 */
	void set(T data);

	/**
	 * @return 数据实际长度
	 */
	Unsigned32 length();
	
	/**
	 * @param 数据实际长度
	 */
	void length(Unsigned32 length);

}
