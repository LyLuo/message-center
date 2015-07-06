package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.attribute.DefaultAttribute;

public class Unsigned32Attribute extends
		DefaultAttribute<Unsigned32> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_10;

	public Unsigned32Attribute() {
		this(new Unsigned32());
	}

	public Unsigned32Attribute(Unsigned32 data) {
		super(data, Unsigned32.class);
	}

}
