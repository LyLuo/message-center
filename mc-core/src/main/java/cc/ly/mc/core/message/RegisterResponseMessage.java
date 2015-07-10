package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.data.impl.Unsigned16;

public class RegisterResponseMessage extends SystemMessage {

	private static final long serialVersionUID = 2131325745982592430L;

	public static final Unsigned16 CODE = Messages.REGISTER_RESPONSE.getCode();

	public RegisterResponseMessage() {
		code = CODE;
	}

	@Override
	public boolean valid() {
		return hasAttribute(Attributes.SUCCESS.getCode());
	}
}
