package cc.ly.mc.core.attribute;

import cc.ly.mc.core.attribute.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息标志位表示类，用于确定数据类型
 * <p>
 * Created by ly on 9/8/15.
 */
public enum AttributeFlag {
    BYTE_ARRAY((byte) 0b00000000, ByteArrayAttribute.class), BOOLEAN((byte) 0b00000001, BooleanAttribute.class, 4),
    UNSIGNED_BYTE((byte) 0b00000010, UnsignedByteAttribute.class, 4), BYTE((byte) 0b00000011, ByteAttribute.class, 4),
    UNSIGNED_SHORT((byte) 0b00000100, UnsignedShortAttribute.class, 5), SHORT((byte) 0b00000101, ShortAttribute.class, 5),
    UNSIGNED_INT((byte) 0b00000110, UnsignedIntAttribute.class, 7), INT((byte) 0b00000111, IntAttribute.class, 7),
    UNSIGNED_LONG((byte) 0b00001000, UnsignedLongAttribute.class, 11), LONG((byte) 0b00001001, LongAttribute.class, 11),
    FLOAT((byte) 0b00001010, FloatAttribute.class, 7), DOUBLE((byte) 0b00001011, DoubleAttribute.class, 11),
    STRING((byte) 0b00001100, StringAttribute.class), GROUPED((byte) 0b00011111, GroupedAttribute.class);

    private byte value;
    private static Map<Byte, AttributeFlag> ATTRIBUTE_FLAGS = new HashMap() {{
        for (AttributeFlag flag : AttributeFlag.values()) {
            if (flag != BYTE_ARRAY) {
                put(flag.value, flag);
            }
        }
    }};
    private Class<? extends Attribute<?>> attributeClass;
    private boolean isFixedLength;
    private int length;

    AttributeFlag(Byte value, Class<? extends Attribute<?>> attributeClass) {
        this(value, attributeClass, false, 0);
    }

    AttributeFlag(Byte value, Class<? extends Attribute<?>> attributeClass, int length) {
        this(value, attributeClass, true, length);
    }

    AttributeFlag(Byte value, Class<? extends Attribute<?>> attributeClass, boolean isFixedLength, int length) {
        this.value = value;
        this.attributeClass = attributeClass;
        this.isFixedLength = isFixedLength;
        this.length = length;
    }

    public static AttributeFlag fromBinary(byte b) {
        if (ATTRIBUTE_FLAGS.containsKey(b)) {
            return ATTRIBUTE_FLAGS.get(b);
        }
        return BYTE_ARRAY;
    }

    public byte value() {
        return value;
    }

    public Class<? extends Attribute<?>> attributeClass() {
        return attributeClass;
    }

    public boolean isFixedLength() {
        return isFixedLength;
    }

    public int length() {
        return length;
    }
}
