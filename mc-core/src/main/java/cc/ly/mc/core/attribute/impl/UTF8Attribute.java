package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.UTF8;

public class UTF8Attribute extends DefaultAttribute<UTF8> {

	public UTF8Attribute() {
		this(new UTF8());
	}

	public UTF8Attribute(UTF8 data) {
		super(data, UTF8.class);
		length = data.length();
	}

}
