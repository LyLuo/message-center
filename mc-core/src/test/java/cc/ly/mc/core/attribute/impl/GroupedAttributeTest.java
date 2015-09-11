package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.Attribute;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ly on 9/10/15.
 */
public class GroupedAttributeTest {

    @Test
    public void decode() {
        GroupedAttribute attribute = new GroupedAttribute();
        byte[] payload = new byte[]{
                0x00,0x01,0x01,0x00,//boolean
                0x00,0x02,0x02,0x01,//unsigned byte;
                0x00,0x03,0x03,0x02,//byte
                0x00,0x04,0x04,0x00,0x03,//unsigned short
                0x00,0x05,0x05,0x00,0x04,//short
                0x00,0x06,0x06,0x00,0x00,0x00,0x05,//unsigned int
                0x00,0x07,0x07,0x00,0x00,0x00,0x06,//int
                0x00,0x08,0x08,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x07,//unsigned long
                0x00,0x09,0x09,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x08,//long
                0x00,0x0a,0x0a,0b01000111, (byte) 0b11000000, (byte) 0b11100110, (byte) 0b10110111,//float
                0x00,0x0b,0x0b,0b01000000, (byte) 0b11001000, 0b00011100, (byte) 0b11010110, (byte) 0b11100110, 0b00110001, (byte) 0b11111000, (byte) 0b10100001,//double
                0x00,0x0c,0x0c,0x00,0x00,0x08,0x41,0x61,//string
                0x00,0x0d,0x00,0x00,0x00,0x08,0x0,0x1,//byte array
                0x00,0x0e, (byte) 0xff,0x00,0x00,0x19,//grouped<boolean,int,string>
                    0x00,0x01,0x01,0x00,
                    0x00,0x07,0x07,0x00,0x00,0x00,0x06,
                    0x00,0x0c,0x0c,0x00,0x00,0x08,0x41,0x61,
        };
        Map<Integer, Attribute<?>> attributes = attribute.dataFromBinary(payload);
        Assert.assertEquals(14,attributes.size());
        Assert.assertEquals(false, attributes.get(1).data());
        Assert.assertEquals(Short.valueOf("1"),attributes.get(2).data());
        Assert.assertEquals(Byte.valueOf("2"),attributes.get(3).data());
        Assert.assertEquals(Integer.valueOf("3"),attributes.get(4).data());
        Assert.assertEquals(Short.valueOf("4"),attributes.get(5).data());
        Assert.assertEquals(Long.valueOf("5"),attributes.get(6).data());
        Assert.assertEquals(Integer.valueOf("6"), attributes.get(7).data());
        Assert.assertEquals("7",attributes.get(8).data());
        Assert.assertEquals(Long.valueOf("8"),attributes.get(9).data());
        Assert.assertEquals(Float.valueOf("98765.4321"),attributes.get(10).data());
        Assert.assertEquals(Double.valueOf("12345.6789"), attributes.get(11).data());
        Assert.assertEquals("Aa", attributes.get(12).data());
        Assert.assertArrayEquals(new byte[]{0, 1}, (byte[]) attributes.get(13).data());
        //grouped attribute in grouped attribute
        Assert.assertEquals(false, ((GroupedAttribute) attributes.get(14)).data().get(1).data());
        Assert.assertEquals(Integer.valueOf("6"), ((GroupedAttribute) attributes.get(14)).data().get(7).data());
        Assert.assertEquals("Aa", ((GroupedAttribute) attributes.get(14)).data().get(12).data());
        RuntimeException re = null;
        try {
            attribute.dataFromBinary(null);
        } catch (IllegalArgumentException e) {
            re = e;
        }
        Assert.assertEquals(IllegalArgumentException.class, re.getClass());
    }

    @Test
    public void encode() {
        GroupedAttribute attribute = new GroupedAttribute();
        BooleanAttribute z = new BooleanAttribute();
        z.code(1);
        z.data(false);
        UnsignedByteAttribute ub = new UnsignedByteAttribute();
        ub.code(2);
        ub.data((short) 1);
        ByteAttribute b = new ByteAttribute();
        b.code(3);
        b.data((byte) 2);
        UnsignedShortAttribute us = new UnsignedShortAttribute();
        us.code(4);
        us.data(3);
        ShortAttribute s = new ShortAttribute();
        s.code(5);
        s.data((short) 4);
        UnsignedIntAttribute ui = new UnsignedIntAttribute();
        ui.code(6);
        ui.data(5L);
        IntAttribute i = new IntAttribute();
        i.code(7);
        i.data(6);
        UnsignedLongAttribute ul = new UnsignedLongAttribute();
        ul.code(8);
        ul.data("7");
        LongAttribute l = new LongAttribute();
        l.code(9);
        l.data(8L);
        FloatAttribute f = new FloatAttribute();
        f.code(10);
        f.data(98765.4321F);
        DoubleAttribute d = new DoubleAttribute();
        d.code(11);
        d.data(12345.6789);
        StringAttribute str = new StringAttribute();
        str.code(12);
        str.data("Aa");
        str.length(8);
        ByteArrayAttribute array = new ByteArrayAttribute();
        array.code(13);
        array.data(new byte[]{0, 1});
        array.length(8);
        GroupedAttribute inner = new GroupedAttribute();
        inner.code(14);
        inner.addAttribute(z);
        inner.addAttribute(i);
        inner.addAttribute(str);
        Map<Integer, Attribute<?>> attributes = new LinkedHashMap<>();
        attributes.put(z.code(), z);
        attributes.put(ub.code(), ub);
        attributes.put(b.code(), b);
        attributes.put(us.code(), us);
        attributes.put(s.code(), s);
        attributes.put(ui.code(), ui);
        attributes.put(i.code(), i);
        attributes.put(ul.code(), ul);
        attributes.put(l.code(), l);
        attributes.put(f.code(), f);
        attributes.put(d.code(), d);
        attributes.put(str.code(), str);
        attributes.put(array.code(), array);
        attributes.put(inner.code(), inner);
        byte[] payload = new byte[]{
                0x00,0x01,0x01,0x00,//boolean
                0x00,0x02,0x02,0x01,//unsigned byte;
                0x00,0x03,0x03,0x02,//byte
                0x00,0x04,0x04,0x00,0x03,//unsigned short
                0x00,0x05,0x05,0x00,0x04,//short
                0x00,0x06,0x06,0x00,0x00,0x00,0x05,//unsigned int
                0x00,0x07,0x07,0x00,0x00,0x00,0x06,//int
                0x00,0x08,0x08,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x07,//unsigned long
                0x00,0x09,0x09,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x08,//long
                0x00,0x0a,0x0a,0b01000111, (byte) 0b11000000, (byte) 0b11100110, (byte) 0b10110111,//float
                0x00,0x0b,0x0b,0b01000000, (byte) 0b11001000, 0b00011100, (byte) 0b11010110, (byte) 0b11100110, 0b00110001, (byte) 0b11111000, (byte) 0b10100001,//double
                0x00,0x0c,0x0c,0x00,0x00,0x08,0x41,0x61,//string
                0x00,0x0d,0x00,0x00,0x00,0x08,0x0,0x1,//byte array
                0x00,0x0e, (byte) 0xff,0x00,0x00,0x19,//grouped<boolean,int,string>
                0x00,0x01,0x01,0x00,
                0x00,0x07,0x07,0x00,0x00,0x00,0x06,
                0x00,0x0c,0x0c,0x00,0x00,0x08,0x41,0x61,
        };
        Assert.assertArrayEquals(payload, attribute.dataToBinary(attributes));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (IllegalArgumentException e) {
            re = e;
        }
        Assert.assertEquals(IllegalArgumentException.class, re.getClass());
    }
}
