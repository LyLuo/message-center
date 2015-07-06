package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.Unsigned16;

public class DeregisterMessage extends SystemMessage {

    public static final Unsigned16 CODE = Messages.DEREGISTER_MESSAGE.getCode();

    public DeregisterMessage() {
        code = CODE;
    }

}