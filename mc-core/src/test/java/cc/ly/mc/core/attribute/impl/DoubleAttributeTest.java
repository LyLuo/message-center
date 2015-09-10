package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class DoubleAttributeTest {

    @Test
    public void decode() {
        DoubleAttribute attribute = new DoubleAttribute();
        Assert.assertEquals(Double.valueOf("0"), attribute.dataFromBinary(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}));
        Assert.assertEquals(Double.valueOf("12345.6789"), attribute.dataFromBinary(new byte[]{0b01000000, (byte) 0b11001000, 0b00011100, (byte) 0b11010110, (byte) 0b11100110, 0b00110001, (byte) 0b11111000, (byte) 0b10100001}));
        Assert.assertEquals(Double.valueOf("-98765.4321"), attribute.dataFromBinary(new byte[]{(byte) 0b11000000, (byte) 0b11111000, (byte) 0b00011100, (byte) 0b11010110, (byte) 0b11101001, (byte) 0b11100001, (byte) 0b10110000, (byte) 0b10001010}));
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
        DoubleAttribute attribute = new DoubleAttribute();
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, attribute.dataToBinary(0.0));
        Assert.assertArrayEquals(new byte[]{0b01000000, (byte) 0b11001000, 0b00011100, (byte) 0b11010110, (byte) 0b11100110, 0b00110001, (byte) 0b11111000, (byte) 0b10100001}, attribute.dataToBinary(12345.6789));
        Assert.assertArrayEquals(new byte[]{(byte) 0b11000000, (byte) 0b11111000, (byte) 0b00011100, (byte) 0b11010110, (byte) 0b11101001, (byte) 0b11100001, (byte) 0b10110000, (byte) 0b10001010}, attribute.dataToBinary(-98765.4321));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (IllegalArgumentException e) {
            re = e;
        }
        Assert.assertEquals(IllegalArgumentException.class, re.getClass());
    }
}
