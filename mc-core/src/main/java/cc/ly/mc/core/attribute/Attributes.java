package cc.ly.mc.core.attribute;

import cc.ly.mc.core.util.NumberUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ly on 9/11/15.
 */
public class Attributes {

    public static final int CODE_FIELD_LENGTH = 2;
    public static final int FLAG_FIELD_LENGTH = 1;
    public static final int LENGTH_FIELD_LENGTH = 3;
    public static final int CODE_FLAG_FIELDS_LENGTH = CODE_FIELD_LENGTH + FLAG_FIELD_LENGTH;
    public static final int CODE_FLAG_LENGTH_FIELDS_LENGTH = CODE_FLAG_FIELDS_LENGTH + LENGTH_FIELD_LENGTH;

    /**
     * 将payload解析为Attributes
     * Attribute = code(2) + flag(1) + length(3) + data
     * @param payload 待解析的数据
     * @return List Attribute
     */
    public static List<Attribute<?>> parse(byte[] payload){
        List<Attribute<?>> attributes = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.wrap(payload);
        while (buffer.hasRemaining()) {
            byte[] codePayload = new byte[CODE_FIELD_LENGTH];
            int code;
            byte flagPayload;
            AttributeFlag flag;
            byte[] lengthPayload = new byte[LENGTH_FIELD_LENGTH];
            int length;
            byte[] attributePayload;
            int remain;
            buffer.get(codePayload);
            code = NumberUtils.bytes2ToUnsignedShort(codePayload);
            flagPayload = buffer.get();
            flag = AttributeFlag.fromBinary(flagPayload);
            Attribute<?> attribute;
            try {
                attribute = flag.attributeClass().newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new IllegalArgumentException("failed to parse attributes", e);
            }
            if(attribute.isFixedLength()){
                length = attribute.length();
                remain = length - CODE_FLAG_FIELDS_LENGTH;
            }else {
                buffer.get(lengthPayload);
                length = NumberUtils.bytes3ToInt(lengthPayload);
                remain = length - CODE_FLAG_LENGTH_FIELDS_LENGTH;
                attribute.length(length);
            }
            if(remain > buffer.remaining()){
                throw new IllegalArgumentException("failed to parse attributes, length " + length + " of one attribute is bigger than length " + buffer.remaining() + " of remain attributes");
            }
            attributePayload = new byte[remain];
            buffer.get(attributePayload);
            attribute.code(code);
            attribute.fromBinary(attributePayload);
            attributes.add(attribute);
        }
        return attributes;
    }

    public static int getLength(Collection<Attribute<?>> attributes){
        if(attributes == null){
            throw new IllegalArgumentException("attributes can not be null");
        }
        return attributes.parallelStream().mapToInt(attribute -> {
            if(!attribute.valid()){
                throw new IllegalArgumentException("attribute is invalid");
            }
            return attribute.length();
        }).sum();
    }

}
