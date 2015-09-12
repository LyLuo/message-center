package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.impl.BooleanAttribute;
import cc.ly.mc.core.attribute.impl.GroupedAttribute;
import cc.ly.mc.core.attribute.impl.IntAttribute;
import cc.ly.mc.core.attribute.impl.StringAttribute;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/11/15.
 */
public class MessageTest {
    @Test
    public void decode(){
        byte[] payload = new byte[]{
                0x01,0x00,0x00,0x35,
                (byte) 0x80,0x00,0x00,0x01,
                0x00,0x00,0x00,0x01,
                0x00,0x00,0x00,0x01,
                0x00,0x01,0x01,0x00,//boolean
                0x00,0x0c,0x0c,0x00,0x00,0x08,0x41,0x61,//string
                0x00,0x0e, (byte) 0xff,0x00,0x00,0x19,//grouped<boolean,int,string>
                0x00,0x01,0x01,0x00,
                0x00,0x07,0x07,0x00,0x00,0x00,0x06,
                0x00,0x0c,0x0c,0x00,0x00,0x08,0x41,0x61,
        };
        Message message = new DefaultMessage();
        message.fromBinary(payload);
        Assert.assertEquals((byte) 1, message.version());
        Assert.assertEquals(53, message.length());
        Assert.assertEquals(MessageFlag.REQUEST, message.flag());
        Assert.assertEquals(1, message.hopByHop());
        Assert.assertEquals(1, message.endToEnd());
        Assert.assertEquals(false, message.attribute(1).data());
        Assert.assertEquals("Aa", message.attribute(12).data());
        Assert.assertEquals(false, ((GroupedAttribute) message.attribute(14)).data().get(1).data());
        Assert.assertEquals(Integer.valueOf("6"), ((GroupedAttribute) message.attribute(14)).data().get(7).data());
        Assert.assertEquals("Aa", ((GroupedAttribute) message.attribute(14)).data().get(12).data());
    }

    @Test
    public void encode(){
        byte[] payload = new byte[]{
                0x01,0x00,0x00,0x35,
                (byte) 0x80,0x00,0x00,0x01,
                0x00,0x00,0x00,0x01,
                0x00,0x00,0x00,0x01,
                0x00,0x01,0x01,0x00,//boolean
                0x00,0x0c,0x0c,0x00,0x00,0x08,0x41,0x61,//string
                0x00,0x0e, (byte) 0xff,0x00,0x00,0x19,//grouped<boolean,int,string>
                0x00,0x01,0x01, 0x00,
                0x00,0x07,0x07,0x00, 0x00,0x00,0x06,
                0x00,0x0c,0x0c,0x00,0x00,0x08,0x41,0x61,
        };
        Message message = new DefaultMessage();
        message.version((byte) 1);
        message.flag(MessageFlag.REQUEST);
        message.code(1);
        message.hopByHop(1);
        message.endToEnd(1);
        BooleanAttribute booleanAttribute = new BooleanAttribute();
        booleanAttribute.code(1);
        booleanAttribute.data(false);
        StringAttribute stringAttribute = new StringAttribute();
        stringAttribute.code(12);
        stringAttribute.data("Aa");
        stringAttribute.length(8);
        BooleanAttribute z = new BooleanAttribute();
        z.code(1);
        z.data(false);
        IntAttribute i = new IntAttribute();
        i.code(7);
        i.data(6);
        StringAttribute str = new StringAttribute();
        str.code(12);
        str.data("Aa");
        str.length(8);
        GroupedAttribute groupedAttribute = new GroupedAttribute();
        groupedAttribute.code(14);
        groupedAttribute.addAttribute(z);
        groupedAttribute.addAttribute(i);
        groupedAttribute.addAttribute(str);
        message.addAttribute(booleanAttribute);
        message.addAttribute(stringAttribute);
        message.addAttribute(groupedAttribute);
        Assert.assertArrayEquals(payload, message.toBinary());
        Assert.assertEquals(false, message.removeAttribute(1).data());
        Assert.assertEquals(49, message.length());
        Assert.assertNull(message.removeAttribute(0));
        message.attach("name", "defaultMessage");
        Assert.assertEquals("defaultMessage", message.attach("name"));
    }
}
