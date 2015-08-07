package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.Unsigned16;

public class ReturnBookMessage extends RelayMessage {

	private static final long serialVersionUID = 1231475739982596434L;

	public static final Unsigned16 CODE = Messages.RETURN_BOOK.getCode();

	public ReturnBookMessage() {
		code = CODE;
	}

}