package cc.ly.mc.core.attribute.impl;

import static cc.ly.mc.core.data.impl.Unsigned32.UNSIGNED32_10;

import cc.ly.mc.core.data.impl.Integer32;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Unsigned32;

public class Integer32Attribute extends
		DefaultAttribute<Integer32> {

	public static final Unsigned32 LENGTH = UNSIGNED32_10;

	public Integer32Attribute() {
		this(new Integer32());
	}

	public Integer32Attribute(Integer32 data) {
		super(data, Integer32.class);
	}

}
