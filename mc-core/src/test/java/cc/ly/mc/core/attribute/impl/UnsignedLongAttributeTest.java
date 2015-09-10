package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class UnsignedLongAttributeTest {

    @Test
    public void decode() {
        UnsignedLongAttribute attribute = new UnsignedLongAttribute();
        Assert.assertEquals("0", attribute.dataFromBinary(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}));
        Assert.assertEquals("4294967295", attribute.dataFromBinary(new byte[]{0, 0, 0, 0, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}));
        Assert.assertEquals("18446744073709551615", attribute.dataFromBinary(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}));
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
        UnsignedLongAttribute attribute = new UnsignedLongAttribute();
        byte[] payload = attribute.dataToBinary("0");
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, payload);
        payload = attribute.dataToBinary("18446744073709551615");
        Assert.assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, payload);
        payload = attribute.dataToBinary("4294967295");
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, payload);
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (IllegalArgumentException e) {
            re = e;
        }
        Assert.assertEquals(IllegalArgumentException.class, re.getClass());
    }
}
