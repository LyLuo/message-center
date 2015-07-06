package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.data.impl.Unsigned8;

public class Unsigned8Attribute extends DefaultAttribute<Unsigned8> {

	public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_7;

	public Unsigned8Attribute() {
		this(new Unsigned8());
	}

	public Unsigned8Attribute(Unsigned8 data) {
		super(data, Unsigned8.class);
	}

}
