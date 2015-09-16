package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.attribute.DefaultAttribute;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ly on 9/10/15.
 */
public class GroupedAttribute extends DefaultAttribute<Map<Integer, Attribute<?>>> {

    public GroupedAttribute(){}

    public GroupedAttribute(int code) {
        super(code, AttributeFlag.GROUPED,0, new HashMap<>());
    }

    public GroupedAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.GROUPED, dataPayload);
    }

    @Override
    public Map<Integer, Attribute<?>> dataFromBinary(byte[] dataPayload) {
        if (dataPayload == null) {
            throw new NullPointerException("GroupedAttribute dataFromBinary payload must not be null");
        }
        Map<Integer, Attribute<?>> map = new LinkedHashMap<>();
        Attributes.parse(dataPayload).forEach(attribute -> map.put(attribute.code(), attribute));
        return map;
    }

    @Override
    public byte[] dataToBinary(Map<Integer, Attribute<?>> attributes) {
        if (attributes == null) {
            throw new NullPointerException("GroupedAttribute dataToBinary attributes must not be null");
        }
        int length = Attributes.getLength(attributes.values());
        ByteBuffer buffer = ByteBuffer.allocate(length);
        attributes.values().forEach(attribute -> buffer.put(attribute.toBinary()));
        return buffer.array();
    }

    public Attribute<?> addAttribute(Attribute<?> attribute) {
        if (attribute == null) {
            throw new NullPointerException("attribute must not be null");
        }
        if (!attribute.valid()) {
            throw new IllegalArgumentException("attribute is invalid");
        }
        if (data().containsKey(attribute.code())) {
            throw new IllegalArgumentException("attribute code already exists");
        }
        this.length = length() + attribute.length();
        return data().put(attribute.code(), attribute);
    }

    public Attribute<?> removeAttribute(int code) {
        if (data().containsKey(code)) {
            Attribute<?> attribute = data().get(code);
            this.length = length() - attribute.length();
        }
        return data().remove(code);
    }

}
