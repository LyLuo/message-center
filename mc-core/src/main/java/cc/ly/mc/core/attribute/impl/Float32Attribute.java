package cc.ly.mc.core.attribute.impl;

import static cc.ly.mc.core.data.impl.Unsigned32.UNSIGNED32_10;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Float32;
import cc.ly.mc.core.data.impl.Unsigned32;

public class Float32Attribute extends DefaultAttribute<Float32> {

	public static final Unsigned32 LENGTH = UNSIGNED32_10;

	public Float32Attribute() {
		this(new Float32());
	}
	
	public Float32Attribute(Float32 data) {
		super(data, Float32.class);
	}


}
