package cc.ly.mc.core.attribute.impl;

import static cc.ly.mc.core.data.impl.Unsigned32.UNSIGNED32_7;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Integer8;
import cc.ly.mc.core.data.impl.Unsigned32;

public class Integer8Attribute extends
		DefaultAttribute<Integer8> {

	public static final Unsigned32 LENGTH = UNSIGNED32_7;

	public Integer8Attribute(){
		this(new Integer8());
	}
	
	public Integer8Attribute(Integer8 data) {
		super(data, Integer8.class);
	}

}
