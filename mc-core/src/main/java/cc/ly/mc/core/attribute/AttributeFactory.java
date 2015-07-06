package cc.ly.mc.core.attribute;

import static cc.ly.mc.core.message.GenericMessage.HEADER_LENGTH;

import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.data.Data;
import cc.ly.mc.core.message.Messages;
import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import cc.ly.mc.core.attribute.impl.IgnoredAttribute;
import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.message.Message;

public class AttributeFactory {
    private final Map<Unsigned16, Class<? extends Attribute<? extends Data<?>>>> code2attributeClass = new HashMap<Unsigned16, Class<? extends Attribute<? extends Data<?>>>>();

    private static class AttributeFactoryHolder {
        private static final AttributeFactory INSTANCE = new AttributeFactory();
    }

    private AttributeFactory() {
    }

    public static AttributeFactory getInstance() {
        return AttributeFactoryHolder.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public void init() throws IOException,
            ClassNotFoundException {
        Attributes[] attributes = Attributes.values();
        for (Attributes attribute : attributes) {
            getInstance().register(attribute.getCode(), attribute.getClazz());
        }
    }

    public void register(Unsigned16 code, Class<? extends Attribute<?>> clazz) {
        code2attributeClass.put(code, clazz);
    }

    public void register(Class<? extends Attribute<?>> clazz) {
        try {
            Unsigned16 code = (Unsigned16) clazz.getDeclaredField("CODE").get(
                    clazz);
            code2attributeClass.put(code, clazz);
        } catch (IllegalArgumentException | IllegalAccessException
                | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public Class<? extends Attribute<? extends Data<?>>> getAttributeClass(
            Unsigned16 code) {
        if (code2attributeClass.containsKey(code)) {
            return code2attributeClass.get(code);
        } else {
            return IgnoredAttribute.class;
        }
    }

    public void generateAttributes(ByteBuf buffer, Message message) {
        Set<Map.Entry<Unsigned16, Attribute<? extends Data<?>>>> attributes = message
                .attributes().entrySet();
        for (Entry<Unsigned16, Attribute<? extends Data<?>>> entry : attributes) {
            buffer.writeBytes(entry.getValue().toBinary());
        }
    }

    public void generateAttributes(Message message, ByteBuf buffer) {
        Unsigned32 left = Unsigned32.sub(message.length(), HEADER_LENGTH);
        while (left.get() > 0) {
            Unsigned16 code = Unsigned16.get(buffer.readUnsignedShort());
            Class<? extends Attribute<? extends Data<?>>> cla = getAttributeClass(code);
            Attribute<? extends Data<?>> attr = null;
            try {
                attr = cla.newInstance();
                attr.fromBinary(buffer);
                attr.code(code);
                message.addAttributeWithoutLengthIncreasing(attr);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            left = Unsigned32.sub(
                    left,
                    Unsigned32.add(code.length(), attr.length().length(),
                            attr.length()));
        }
    }

}
