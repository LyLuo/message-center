package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class UnsignedShortAttributeTest {

    @Test
    public void decode() {
        UnsignedShortAttribute attribute = new UnsignedShortAttribute();
        Assert.assertEquals(Integer.valueOf("0"), attribute.dataFromBinary(new byte[]{0, 0}));
        Assert.assertEquals(Integer.valueOf(0xFFFF), attribute.dataFromBinary(new byte[]{(byte) 0xff, (byte) 0xff}));
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
        UnsignedShortAttribute attribute = new UnsignedShortAttribute();
        Assert.assertArrayEquals(new byte[]{0, 0}, attribute.dataToBinary(0));
        Assert.assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff}, attribute.dataToBinary(0xFFFF));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (IllegalArgumentException e) {
            re = e;
        }
        Assert.assertEquals(IllegalArgumentException.class, re.getClass());
    }
}
