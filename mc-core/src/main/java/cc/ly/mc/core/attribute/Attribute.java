package cc.ly.mc.core.attribute;

import cc.ly.mc.core.data.Data;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.io.Binary;
import cc.ly.mc.core.data.impl.Unsigned32;

/**
 * 熟悉基础接口
 * 
 * @author ly
 * 
 * @param <T>
 *            具体Data类型
 */
public interface Attribute<T extends Data<?>> extends Binary {

	/**
	 * @return 属性编码
	 */
	Unsigned16 code();
	
	/**
	 * 设置属性编码
	 * @para 属性编码
	 */
	void code(Unsigned16 code);

	/**
	 * @return 属性长度
	 */
	Unsigned32 length();

	/**
	 * @return 具体数据
	 */
	T data();

	/**
	 * @param data
	 *            具体数据
	 */
	void data(T data);

}
