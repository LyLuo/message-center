package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class ShortAttributeTest {

    @Test
    public void decode() {
        ShortAttribute attribute = new ShortAttribute();
        Assert.assertEquals(Short.valueOf("0"), attribute.dataFromBinary(new byte[]{0, 0}));
        Assert.assertEquals(Short.valueOf(Short.MAX_VALUE), attribute.dataFromBinary(new byte[]{0x7f, (byte) 0xff}));
        Assert.assertEquals(Short.valueOf(Short.MIN_VALUE), attribute.dataFromBinary(new byte[]{(byte) 0x80, (byte) 0x00}));
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
        ShortAttribute attribute = new ShortAttribute();
        Assert.assertArrayEquals(new byte[]{0, 0}, attribute.dataToBinary(Short.valueOf("0")));
        Assert.assertArrayEquals(new byte[]{0x7f, (byte) 0xff}, attribute.dataToBinary(Short.valueOf(Short.MAX_VALUE)));
        Assert.assertArrayEquals(new byte[]{(byte) 0x80, (byte) 0x00}, attribute.dataToBinary(Short.valueOf(Short.MIN_VALUE)));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (NullPointerException e) {
            re = e;
        }
        Assert.assertEquals(NullPointerException.class, re.getClass());
    }
}
