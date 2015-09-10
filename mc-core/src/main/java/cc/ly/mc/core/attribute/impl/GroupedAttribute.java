package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.attribute.DefaultAttribute;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ly on 9/10/15.
 */
public class GroupedAttribute extends DefaultAttribute<ConcurrentHashMap<Short, Attribute<?>>> {
    @Override
    public ConcurrentHashMap<Short, Attribute<?>> dataFromBinary(byte[] payload) {
        return null;
    }

    @Override
    public byte[] dataToBinary(ConcurrentHashMap<Short, Attribute<?>> shortAttributeConcurrentHashMap) {
        return new byte[0];
    }
}
