package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.data.impl.Unsigned16;
import org.w3c.dom.Attr;

/**
 * Created by Administrator on 2015/6/29.
 */
public class BorrowMessage extends RelayMessage {

    private static final long serialVersionUID = 2631395733981296034L;

    public static final Unsigned16 CODE = Messages.BORROW.getCode();

    public BorrowMessage() {
        code = CODE;
    }

    @Override
    public boolean valid() {
        if (super.valid()) {
            return hasAttribute(Attributes.BOOK_ID.getCode(), Attributes.BOOK_NAME.getCode());
        }
        return false;
    }
}
