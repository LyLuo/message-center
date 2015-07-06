package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.data.impl.Bool;
import cc.ly.mc.core.data.impl.UTF8;

public class BoolAttribute extends DefaultAttribute<Bool> {

	public static final BoolAttribute TRUE = new BoolAttribute(Bool.TRUE);

	public static final BoolAttribute FALSE = new BoolAttribute(Bool.FALSE);

	public BoolAttribute() {
		this(Bool.TRUE);
	}

	public BoolAttribute(Bool data) {
		super(data, Bool.class);
		length = data.length();
	}

}
