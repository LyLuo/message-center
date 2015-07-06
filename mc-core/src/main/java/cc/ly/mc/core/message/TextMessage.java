package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.Unsigned16;

public class TextMessage extends RelayMessage {

	private static final long serialVersionUID = 1131375735982596434L;

	public static final Unsigned16 CODE = Messages.TEXT_MESSAGE.getCode();

	public TextMessage() {
		code = CODE;
	}

}