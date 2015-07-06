package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.AttributeFactory;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.data.Data;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.data.impl.Unsigned8;

public class MessageFactory {
    private final Map<Field, Class<? extends Data<?>>> forDecodingMessageHeader = new LinkedHashMap<Field, Class<? extends Data<?>>>();
    private final Map<Field, Class<? extends Data<?>>> forEncodingMessageHeader = new LinkedHashMap<Field, Class<? extends Data<?>>>();
    private AttributeFactory attributeFactory;
    private final Map<Unsigned16, Class<? extends Message>> code2messageClass = new HashMap<Unsigned16, Class<? extends Message>>();

    private static class MessageFactoryHolder {
        private static final MessageFactory INSTANCE = new MessageFactory();
    }

    private MessageFactory() {
        initForDecodingGenericMessageHeader();
        initForEncodingGenericMessageHeader();
        attributeFactory = AttributeFactory.getInstance();
    }

    public static MessageFactory getInstance() {
        return MessageFactoryHolder.INSTANCE;
    }

    private void initForDecodingGenericMessageHeader() {
        try {
            forDecodingMessageHeader.put(
                    GenericMessage.class.getDeclaredField("flag"),
                    FlagData.class);
            forDecodingMessageHeader.put(
                    GenericMessage.class.getDeclaredField("length"),
                    Unsigned32.class);
            forDecodingMessageHeader.put(
                    GenericMessage.class.getDeclaredField("hopByHop"),
                    Integer64.class);
            forDecodingMessageHeader.put(
                    GenericMessage.class.getDeclaredField("endToEnd"),
                    Integer64.class);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private void initForEncodingGenericMessageHeader() {
        try {
            forEncodingMessageHeader.put(
                    GenericMessage.class.getDeclaredField("version"),
                    Unsigned8.class);
            forEncodingMessageHeader.put(
                    GenericMessage.class.getDeclaredField("code"),
                    Unsigned16.class);
            forEncodingMessageHeader.putAll(forDecodingMessageHeader);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void initMessages() throws IOException,
            ClassNotFoundException {
        Messages[] messages = Messages.values();
        for (Messages message : messages) {
            getInstance().register(message.getClazz());
        }
    }

    public void initAttributes() throws IOException,
            ClassNotFoundException {
        attributeFactory.init();
    }

    public void register(Class<? extends Message> clazz) {
        try {
            Unsigned16 code = (Unsigned16) clazz.getDeclaredField("CODE").get(
                    clazz);
            code2messageClass.put(code, clazz);
        } catch (IllegalArgumentException | IllegalAccessException
                | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public void register(Unsigned16 code, Class<? extends Message> clazz) {
        code2messageClass.put(code, clazz);
    }

    public void registerAttribute(Unsigned16 code,
                                  Class<? extends Attribute<? extends Data<?>>> clazz) {
        attributeFactory.register(code, clazz);
    }

    public void registerAttribute(
            Class<? extends Attribute<? extends Data<?>>> clazz) {
        attributeFactory.register(clazz);
    }

    public Class<? extends Message> getMessageClass(Unsigned16 code) {
        return code2messageClass.get(code);
    }

    private Set<Map.Entry<Field, Class<? extends Data<?>>>> getMessageHeaderForDecoding() {
        return forDecodingMessageHeader.entrySet();
    }

    private Set<Map.Entry<Field, Class<? extends Data<?>>>> getMessageHeaderForEncoding() {
        return forEncodingMessageHeader.entrySet();
    }

    public Message createMessage(ByteBuf buffer) {
        Unsigned8 version = Unsigned8.get(buffer.readUnsignedByte());
        if (version != GenericMessage.VERSION) {
            throw new IllegalArgumentException("version " + version.get()
                    + " must be " + GenericMessage.VERSION.get());
        }
        Unsigned16 code = Unsigned16.get(buffer.readUnsignedShort());
        Message message;
        try {
            message = getMessageClass(code).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("code " + code.get()
                    + " is not supported !", e);
        }
        message.code(code);
        message.fromBinary(buffer);
        return message;
    }

    public void generateMessage(Message message, ByteBuf buffer) {
        generateMessageHeader(message, buffer);
        attributeFactory.generateAttributes(message, buffer);
    }

    private void generateMessageHeader(Message message, ByteBuf buffer) {
        Set<Map.Entry<Field, Class<? extends Data<?>>>> headerSet = getMessageHeaderForDecoding();
        try {
            for (Map.Entry<Field, Class<? extends Data<?>>> entry : headerSet) {
                entry.getKey().setAccessible(true);
                entry.getKey().set(message, entry.getValue().newInstance());
                ((Data<?>) entry.getKey().get(message)).fromBinary(buffer);
            }
        } catch (IllegalArgumentException | IllegalAccessException
                | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public ByteBuf generateByteBuf(Message message) {
        ByteBuf buffer = generateHeader(message, getMessageHeaderForEncoding());
        attributeFactory.generateAttributes(buffer, message);
        return buffer;
    }

    private ByteBuf generateHeader(Message message,
                                   Set<Map.Entry<Field, Class<? extends Data<?>>>> headerSet) {
        ByteBuf buffer = Unpooled.buffer(message.length().get().intValue());
        for (Map.Entry<Field, Class<? extends Data<?>>> entry : headerSet) {
            try {
                entry.getKey().setAccessible(true);
                buffer.writeBytes(((Data<?>) entry.getKey().get(message))
                        .toBinary());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }

}
