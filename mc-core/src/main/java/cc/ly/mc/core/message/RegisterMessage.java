package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.data.impl.Unsigned16;

public class RegisterMessage extends SystemMessage {

	private static final long serialVersionUID = -3062023030830378449L;

	public static final transient Unsigned16 CODE = Messages.REGISTER.getCode();

	public RegisterMessage() {
		code = CODE;
	}

	@Override
	public boolean valid() {
		return hasAttribute(Attributes.SENDER_ID.getCode(), Attributes.TOKEN.getCode());
	}
}