package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.data.impl.Integer64;

public class Integer64Attribute extends DefaultAttribute<Integer64> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_14;

	public Integer64Attribute() {
		this(new Integer64(0L));
	}

	public Integer64Attribute(Integer64 data) {
		super(data, Integer64.class);
	}

}
