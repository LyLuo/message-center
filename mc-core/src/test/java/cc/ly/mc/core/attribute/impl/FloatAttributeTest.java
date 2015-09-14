package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class FloatAttributeTest {

    @Test
    public void decode() {
        FloatAttribute attribute = new FloatAttribute();
        Assert.assertEquals(Float.valueOf("0"), attribute.dataFromBinary(new byte[]{0, 0, 0, 0}));
        Assert.assertEquals(Float.valueOf("98765.4321"), attribute.dataFromBinary(new byte[]{0b01000111, (byte) 0b11000000, (byte) 0b11100110, (byte) 0b10110111}));
        Assert.assertEquals(Float.valueOf("-12345.6789"), attribute.dataFromBinary(new byte[]{(byte) 0b11000110, 0b01000000, (byte) 0b11100110, (byte) 0b10110111}));
        RuntimeException re = null;
        try {
            attribute.dataFromBinary(new byte[0]);
        } catch (IllegalArgumentException e) {
            re = e;
        }
        Assert.assertEquals(IllegalArgumentException.class, re.getClass());
    }

    @Test
    public void encode() {
        FloatAttribute attribute = new FloatAttribute();
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0}, attribute.dataToBinary(0F));
        Assert.assertArrayEquals(new byte[]{0b01000111, (byte) 0b11000000, (byte) 0b11100110, (byte) 0b10110111}, attribute.dataToBinary(98765.4321F));
        Assert.assertArrayEquals(new byte[]{(byte) 0b11000110, 0b01000000, (byte) 0b11100110, (byte) 0b10110111}, attribute.dataToBinary(-12345.6789F));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (NullPointerException e) {
            re = e;
        }
        Assert.assertEquals(NullPointerException.class, re.getClass());
    }
}
