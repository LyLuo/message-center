package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.data.impl.Unsigned16;

/**
 * Created by Administrator on 2015/7/8.
 */
public class BorrowResponseMessage extends RelayMessage {

    private static final long serialVersionUID = 2631395733981296034L;

    public static final Unsigned16 CODE = Messages.BORROW_RESPONSE.getCode();

    public BorrowResponseMessage() {
        code = CODE;
    }

    @Override
    public boolean valid() {
        if (super.valid()) {
            return hasAttribute(Attributes.SUCCESS.getCode());
        }
        return false;
    }
}
