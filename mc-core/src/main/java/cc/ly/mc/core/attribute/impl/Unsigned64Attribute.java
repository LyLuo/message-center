package cc.ly.mc.core.attribute.impl;

import static cc.ly.mc.core.data.impl.Unsigned32.UNSIGNED32_14;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.data.impl.Unsigned64;

public class Unsigned64Attribute extends
		DefaultAttribute<Unsigned64> {

	public static final Unsigned32 LENGTH = UNSIGNED32_14;

	public Unsigned64Attribute() {
		this(new Unsigned64());
	}
	
	public Unsigned64Attribute(Unsigned64 data) {
		super(data, Unsigned64.class);
	}

}
