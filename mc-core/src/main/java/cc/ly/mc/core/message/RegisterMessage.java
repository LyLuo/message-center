package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.data.impl.Unsigned16;

public class RegisterMessage extends SystemMessage {

	private static final long serialVersionUID = -3062023030830378449L;

	public static final transient Unsigned16 CODE = Unsigned16.get(3);

	public RegisterMessage() {
		code = CODE;
	}

	@Override
	public boolean valid() {
		return hasAttribute(Attributes.TOKEN.getCode());
	}
}