package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.attribute.DefaultAttribute;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ly on 9/10/15.
 */
public class GroupedAttribute extends DefaultAttribute<Map<Integer, Attribute<?>>> {
    @Override
    public Map<Integer, Attribute<?>> dataFromBinary(byte[] payload) {
        if(payload == null){
            throw new IllegalArgumentException("GroupedAttribute dataFromBinary payload must not be null");
        }
        Map<Integer, Attribute<?>> map = new ConcurrentHashMap<>();
        DefaultAttribute.parse(payload).forEach(attribute -> map.put(attribute.code(), attribute));
        return map;
    }

    @Override
    public byte[] dataToBinary(Map<Integer, Attribute<?>> attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException("GroupedAttribute dataToBinary attributes must not be null");
        }
        ByteBuffer buffer = ByteBuffer.allocate(length() - 6);
        attributes.values().forEach(attribute -> buffer.put(attribute.toBinary()));
        return buffer.array();
    }
}
