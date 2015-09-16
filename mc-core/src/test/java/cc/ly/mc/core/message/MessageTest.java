package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.DefaultAttribute;
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
                0x00,0x0e, (byte) 0x1f,0x00,0x00,0x19,//grouped<boolean,int,string>
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
                0x00,0x0e, (byte) 0x1f,0x00,0x00,0x19,//grouped<boolean,int,string>
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
        BooleanAttribute booleanAttribute = new BooleanAttribute(1,false);
        StringAttribute stringAttribute = new StringAttribute(12,"Aa");
        BooleanAttribute z = new BooleanAttribute(1,false);
        IntAttribute i = new IntAttribute(7,6);
        StringAttribute str = new StringAttribute(12,"Aa");
        GroupedAttribute groupedAttribute = new GroupedAttribute(14);
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

    @Test
    public void testOther(){
        DefaultMessage message = new DefaultMessage();
        message.addAttribute(new StringAttribute(1, "string"));
        Assert.assertEquals(true, message.hasAttribute(1));
        message.removeAttribute(1);
        Assert.assertEquals(false, message.hasAttribute(1));
        message.attach("info", "this is string attribute");
        Assert.assertEquals("this is string attribute", message.attach("info"));
        Exception actual = null;
        IntAttribute b1 = new IntAttribute(1, 1);
        IntAttribute b2 = new IntAttribute(1, 1);
        try {
            message.addAttribute(b1);
            message.addAttribute(b2);
        } catch (Exception e){
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(IllegalArgumentException.class, actual.getClass());
        try {
            message.addAttribute(new DefaultAttribute() {
                @Override
                public Object dataFromBinary(byte[] payload) {
                    return null;
                }

                @Override
                public byte[] dataToBinary(Object o) {
                    return new byte[0];
                }
            });
        } catch (Exception e){
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(IllegalArgumentException.class, actual.getClass());
        try {
            message.addAttribute(null);
        } catch (Exception e){
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(NullPointerException.class, actual.getClass());
        try {
            message.attach(null);
        } catch (Exception e){
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(NullPointerException.class, actual.getClass());
        try {
            message.attach(null, "");
        } catch (Exception e){
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(NullPointerException.class, actual.getClass());
        Assert.assertTrue(message.valid());
        try {
            Assert.assertEquals(false, message.hasAttribute(null));
        }catch (Exception e){
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(NullPointerException.class, actual.getClass());
    }
}
