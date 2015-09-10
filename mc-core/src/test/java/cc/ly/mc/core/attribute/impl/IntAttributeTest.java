package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class IntAttributeTest {

    @Test
    public void decode() {
        IntAttribute attribute = new IntAttribute();
        Assert.assertEquals(Integer.valueOf("0"), attribute.dataFromBinary(new byte[]{0, 0, 0, 0}));
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), attribute.dataFromBinary(new byte[]{0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff}));
        Assert.assertEquals(Integer.valueOf(Integer.MIN_VALUE), attribute.dataFromBinary(new byte[]{(byte) 0x80, 0x00, 0x00, 0x00}));
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
        IntAttribute attribute = new IntAttribute();
        byte[] payload = attribute.dataToBinary(Integer.valueOf("0"));
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0}, payload);
        payload = attribute.dataToBinary(Integer.valueOf(Integer.MAX_VALUE));
        Assert.assertArrayEquals(new byte[]{0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff}, payload);
        payload = attribute.dataToBinary(Integer.valueOf(Integer.MIN_VALUE));
        Assert.assertArrayEquals(new byte[]{(byte) 0x80, 0x00, 0x00, 0x00}, payload);
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (IllegalArgumentException e) {
            re = e;
        }
        Assert.assertEquals(IllegalArgumentException.class, re.getClass());
    }
}
