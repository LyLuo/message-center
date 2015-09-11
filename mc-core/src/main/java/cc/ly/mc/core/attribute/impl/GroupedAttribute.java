package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.attribute.DefaultAttribute;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ly on 9/10/15.
 */
public class GroupedAttribute extends DefaultAttribute<Map<Integer, Attribute<?>>> {

    public GroupedAttribute() {
        this.flag = AttributeFlag.GROUPED;
        this.data = new LinkedHashMap<>();
        this.length = Attributes.CODE_FLAG_LENGTH_FIELDS_LENGTH;
    }

    @Override
    public boolean isFixedLength() {
        return false;
    }

    @Override
    public Map<Integer, Attribute<?>> dataFromBinary(byte[] payload) {
        if (payload == null) {
            throw new IllegalArgumentException("GroupedAttribute dataFromBinary payload must not be null");
        }
        Map<Integer, Attribute<?>> map = new LinkedHashMap<>();
        Attributes.parse(payload).forEach(attribute -> map.put(attribute.code(), attribute));
        return map;
    }

    @Override
    public byte[] dataToBinary(Map<Integer, Attribute<?>> attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException("GroupedAttribute dataToBinary attributes must not be null");
        }
        int length = Attributes.getLength(attributes.values());
        ByteBuffer buffer = ByteBuffer.allocate(length);
        attributes.values().forEach(attribute -> buffer.put(attribute.toBinary()));
        return buffer.array();
    }

    public Attribute<?> addAttribute(Attribute<?> attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException("attribute must not be null");
        }
        if (!attribute.valid()) {
            throw new IllegalArgumentException("attribute is invalid");
        }
        if (data().containsKey(attribute.code())) {
            throw new IllegalArgumentException("attribute code already exists");
        }
        length(length() + attribute.length());
        return data().put(attribute.code(), attribute);
    }

    public Attribute<?> removeAttribute(int code) {
        if (data().containsKey(code)) {
            Attribute<?> attribute = data().get(code);
            length(length() - attribute.length());
        }
        return data().remove(code);
    }

}
