package cc.ly.mc.core.attribute;

import cc.ly.mc.core.attribute.impl.BoolAttribute;
import cc.ly.mc.core.attribute.impl.Integer32Attribute;
import cc.ly.mc.core.attribute.impl.Integer64Attribute;
import cc.ly.mc.core.attribute.impl.UTF8Attribute;
import cc.ly.mc.core.data.impl.Integer32;
import cc.ly.mc.core.data.impl.Unsigned16;

/**
 * Created by Administrator on 2015/6/18.
 */
public enum Attributes {
    SUCCESS(0, BoolAttribute.class),
    TOKEN(1, UTF8Attribute.class),
    SENDER_ID(2, Integer32Attribute.class),
    SENDER_NAME(3, UTF8Attribute.class),
    RECEIVER_ID(4, Integer32Attribute.class),
    RECEIVER_NAME(5, UTF8Attribute.class),
    CHAT_CONTENT(6, UTF8Attribute.class),
    BOOK_ID(7, Integer32Attribute.class),
    BOOK_NAME(8, UTF8Attribute.class);
    private Unsigned16 code;
    private Class<? extends Attribute<?>> clazz;

    Attributes(int code, Class<? extends Attribute<?>> clazz) {
        this.code = Unsigned16.get(code);
        this.clazz = clazz;
    }

    public Unsigned16 getCode() {
        return code;
    }

    public Class<? extends Attribute<?>> getClazz() {
        return clazz;
    }
}

