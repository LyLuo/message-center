package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.data.impl.Integer16;
import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.attribute.DefaultAttribute;

public class Integer16Attribute extends
		DefaultAttribute<Integer16> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_8;
	
	public Integer16Attribute() {
		this(new Integer16());
	}

	public Integer16Attribute(Integer16 data) {
		super(data, Integer16.class);
	}


}
