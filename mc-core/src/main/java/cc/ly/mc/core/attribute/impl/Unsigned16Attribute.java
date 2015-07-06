package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.data.impl.Unsigned32;

public class Unsigned16Attribute extends
		DefaultAttribute<Unsigned16> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_8;

	public Unsigned16Attribute() {
		this(new Unsigned16());
	}

	public Unsigned16Attribute(Unsigned16 data) {
		super(data, Unsigned16.class);
	}

}
