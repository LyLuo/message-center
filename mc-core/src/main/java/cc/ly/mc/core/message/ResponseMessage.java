package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.Unsigned16;

public class ResponseMessage extends GenericMessage {

	private static final long serialVersionUID = 2131325745982592430L;

	public static final Unsigned16 CODE = Unsigned16.get(0);

	public ResponseMessage() {
		code = CODE;
	}

}
