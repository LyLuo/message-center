package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.Unsigned16;

/**
 * Created by Administrator on 2015/6/29.
 */
public class BorrowingMessage extends RelayMessage {

    private static final long serialVersionUID = 2631395733981296034L;

    public static final Unsigned16 CODE = Messages.BORROWING_MESSAGE.getCode();

    public BorrowingMessage() {
        code = CODE;
    }
}
