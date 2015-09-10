package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.event.EventBus;
import cc.ly.mc.core.util.NumberUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ly on 9/8/15.
 */
public class DefaultMessage implements Message {

    private byte version;

    private int length;

    private MessageFlag flag;

    private int code;

    private int hopByHop;

    private int endToEnd;

    private Map<Integer, Attribute<?>> attributes = new HashMap<>();

    private Map<Object, Object> attachs = new HashMap<>();

    @Override
    public byte version() {
        return version;
    }

    @Override
    public void version(byte version) {
        this.version = version;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public void length(int length) {
        this.length = length;
    }

    @Override
    public MessageFlag flag() {
        return flag;
    }

    @Override
    public void flag(MessageFlag flag) {
        this.flag = flag;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public void code(int code) {
        this.code = code;
    }

    @Override
    public int hopByHop() {
        return hopByHop;
    }

    @Override
    public void hopByHop(int hopByHop) {
        this.hopByHop = hopByHop;
    }

    @Override
    public int endToEnd() {
        return endToEnd;
    }

    @Override
    public void endToEnd(int endToEnd) {
        this.endToEnd = endToEnd;
    }

    @Override
    public Attribute<?> attribute(int code) {
        return attributes.get(code);
    }

    @Override
    public boolean hasAttribute(int... codes) {
        return attributes.keySet().containsAll(Arrays.asList(codes));
    }

    @Override
    public Map<Integer, Attribute<?>> attributes() {
        return attributes;
    }

    @Override
    public void addAttribute(Attribute<?> attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException("attribute must not be null");
        }
        attributes.put(attribute.code(), attribute);
    }

    @Override
    public void attach(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        attachs.put(key, value);
    }

    @Override
    public Object attach(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        return attachs.get(key);
    }

    @Override
    public boolean valid() {
        return true;
    }

    /**
     * verison(1) + length(3) + flag(1) + code(3) + hopByHop(4) + hopByHop(4) + attributes
     *
     * @param payload 二进制数据
     */
    @Override
    public void fromBinary(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload);
        buffer.flip();
        //parse version
        version(buffer.get());
        //parse length
        byte[] lengthPayload = new byte[3];
        buffer.get(lengthPayload);
        length(NumberUtils.bytes3ToInt(lengthPayload));
        if (payload.length != length()) {
            throw new IllegalArgumentException("message's length " + payload.length + " + not equal length attribute " + length());
        }
        //parse flag
        flag(MessageFlag.fromBinary(buffer.get()));
        //parse code
        byte[] codePayload = new byte[3];
        buffer.get(codePayload);
        code(NumberUtils.bytes3ToInt(codePayload));
        //parse hopByHop
        hopByHop(buffer.getInt());
        //parse endToEnd
        endToEnd(buffer.getInt());
        //parse attributes
        DefaultAttribute.parse(buffer.array()).forEach(attribute -> {
            addAttribute(attribute);
        });
    }



    @Override
    public byte[] toBinary() {
        ByteBuffer buffer = ByteBuffer.allocate(length());
        buffer.put(version());
        buffer.put(NumberUtils.intToBytes3(length()));
        buffer.put(flag().value());
        buffer.put(NumberUtils.intToBytes3(code()));
        buffer.putInt(hopByHop());
        buffer.putInt(endToEnd());
        attributes().forEach((integer, attribute) -> buffer.put(attribute.toBinary()));
        buffer.flip();
        return buffer.array();
    }

    /**
     * notify以当前对象类名注册名的观察者，并将自身作为event source传入
     */
    @Override
    public void onReceived() {
        EventBus.getInstance().notify(this.getClass().getSimpleName(), this);
    }
}
