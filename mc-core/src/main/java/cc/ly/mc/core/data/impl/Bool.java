package cc.ly.mc.core.data.impl;

import cc.ly.mc.core.data.DefaultData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Bool extends DefaultData<Boolean> {

    public static final Unsigned32 LENGTH = Unsigned32.UNSIGNED32_1;

    public static final Bool TRUE = new Bool(true);

    public static final Bool FALSE = new Bool(false);

    public Bool(){
        this(true);
    }

    public Bool(boolean data) {
        super(data);
        this.length = LENGTH;
    }

    public Unsigned32 length() {
        return length;
    }

    public void fromBinary(ByteBuf buffer) {
        data = buffer.readBoolean();
    }

    public ByteBuf toBinary() {
        return Unpooled.copyBoolean(data);
    }

}
