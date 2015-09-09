package cc.ly.mc.core.attribute;

import cc.ly.mc.core.attribute.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息标志位表示类，用于确定数据类型
 * <p/>
 * Created by ly on 9/8/15.
 */
public enum AttributeFlag {
    IGNORED(null, IgnoredDataTypeAttribute.class), BOOLEAN((byte)0b00000001, BooleanAttribute.class),
    UNSIGNED_BYTE((byte) 0b00000010, UnsignedByteAttribute.class), BYTE((byte) 0b00000011, ByteAttribute.class),
    UNSIGNED_SHORT((byte) 0b00000100, UnsignedShortAttribute.class), SHORT((byte) 0b00000101, ShortAttribute.class),
    UNSIGNED_INT((byte) 0b00000110, UnsignedIntAttribute.class), INT((byte) 0b00000111, IntAttribute.class),
    UNSIGNED_LONG((byte) 0b00001000, UnsignedLongAttribute.class), LONG((byte) 0b00001001, LongAttribute.class),
    FLOAT((byte) 0b00001010, FloatAttribute.class), DOUBLE((byte) 0b00001011, DoubleAttribute.class), GROUPED((byte) 0b00000000, null);

    private Byte value;
    private Class<? extends Attribute<?>> attributeClass;
    private static Map<Byte, AttributeFlag> ATTRIBUTE_FLAGS = new HashMap() {{
        for (AttributeFlag flag : AttributeFlag.values()) {
            if (flag != IGNORED) {
                put(flag.value, flag);
            }
        }
    }};

    AttributeFlag(Byte value, Class<? extends Attribute<?>> attributeClass) {
        this.value = value;
        this.attributeClass = attributeClass;
    }

    public static AttributeFlag fromBinary(byte b) {
        if (ATTRIBUTE_FLAGS.containsKey(b)) {
            return ATTRIBUTE_FLAGS.get(b);
        }
        return IGNORED;
    }

    public byte value() {
        return value;
    }

    public Class<? extends Attribute<?>> attributeClass() {
        return attributeClass;
    }
}
