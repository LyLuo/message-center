package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.IgnoredData;

public class IgnoredAttribute extends DefaultAttribute<IgnoredData> {

	public IgnoredAttribute() {
		this(new IgnoredData());
	}
	
	public IgnoredAttribute(IgnoredData data) {
		super(data, IgnoredData.class);
		this.length = data.length();
	}

}
