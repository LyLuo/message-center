package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.Unsigned16;

/**
 * Created by Administrator on 2015/7/8.
 */
public class DeregisterResponseMessage extends SystemMessage {

    public static final Unsigned16 CODE = Messages.DEREGISTER_RESPONSE.getCode();

    public DeregisterResponseMessage() {
        code = CODE;
    }

}
