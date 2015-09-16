package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.event.EventBus;
import cc.ly.mc.core.util.NumberUtils;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by ly on 9/8/15.
 */
public class DefaultMessage implements Message {

    private byte version;

    private int length = Messages.MESSAGE_FIELDS_LENGTH;

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
        if(codes == null){
            throw new NullPointerException("codes must not be null");
        }
        Set<Integer> keySet = attributes.keySet();
        for(int i = 0, size = codes.length; i < size; i++) {
            if(!keySet.contains(codes[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Integer, Attribute<?>> attributes() {
        return attributes;
    }

    @Override
    public void addAttribute(Attribute<?> attribute) {
        if (attribute == null) {
            throw new NullPointerException("attribute must not be null");
        }
        if (! attribute.valid()) {
            throw new IllegalArgumentException("attribute is invalid");
        }
        if (attributes.containsKey(attribute.code())){
            throw new IllegalArgumentException("attribute already exits");
        }
        attributes.put(attribute.code(), attribute);
        length += attribute.length();
    }

    @Override
    public Attribute<?> removeAttribute(int code) {
        if(attributes().containsKey(code)){
            Attribute<?> attribute = attributes().remove(code);
            this.length = length() - attribute.length();
            return attribute;
        }
        return null;
    }

    @Override
    public void attach(Object key, Object value) {
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
        attachs.put(key, value);
    }

    @Override
    public Object attach(Object key) {
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
        return attachs.get(key);
    }

    @Override
    public boolean valid() {
        return true;
    }

    /**
     * verison(1) + length(3) + flag(1) + code(3) + hopByHop(4) + endToEnd(4) + attributes
     *
     * @param payload 二进制数据
     */
    @Override
    public void fromBinary(byte[] payload) {
        if (payload.length < Messages.MESSAGE_FIELDS_LENGTH){
            throw new IllegalArgumentException("payload's length must bigger than " + Messages.MESSAGE_FIELDS_LENGTH + " but it's " + length);
        }
        byte[] lengthPayload = new byte[Messages.LENGTH_FIELD_LENGTH];
        byte[] codePayload = new byte[Messages.CODE_FIELD_LENGTH];
        byte[] attributesPayload;
        int length;
        ByteBuffer buffer = ByteBuffer.wrap(payload);
        //parse version
        version(buffer.get());
        //parse length
        buffer.get(lengthPayload);
        length = NumberUtils.bytes3ToInt(lengthPayload);
        if (length < Messages.MESSAGE_FIELDS_LENGTH){
            throw new IllegalArgumentException("message's length must bigger than " + Messages.MESSAGE_FIELDS_LENGTH + " but it's " + length);
        }
        if (payload.length < length) {
            throw new IllegalArgumentException("message length " + length + " must less than payload's length " + payload.length);
        }
        //parse flag
        flag(MessageFlag.fromBinary(buffer.get()));
        //parse code
        buffer.get(codePayload);
        code(NumberUtils.bytes3ToInt(codePayload));
        //parse hopByHop
        hopByHop(buffer.getInt());
        //parse endToEnd
        endToEnd(buffer.getInt());
        attributesPayload = new byte[length - Messages.MESSAGE_FIELDS_LENGTH];
        buffer.get(attributesPayload);
        //parse attributes
        Attributes.parse(attributesPayload).forEach(attribute -> addAttribute(attribute));
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
        EventBus.getInstance().notify(String.valueOf(this.code()), this);
    }
}
