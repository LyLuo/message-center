package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Float64;
import cc.ly.mc.core.data.impl.Unsigned32;

public class Float64Attribute extends DefaultAttribute<Float64> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_14;

	public Float64Attribute() {
		this(new Float64());
	}
	
	public Float64Attribute(Float64 data) {
		super(data, Float64.class);
	}

}
