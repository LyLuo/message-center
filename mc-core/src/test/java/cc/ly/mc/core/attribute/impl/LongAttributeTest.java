package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class LongAttributeTest {

    @Test
    public void decode() {
        LongAttribute attribute = new LongAttribute();
        Assert.assertEquals(Long.valueOf("0"), attribute.dataFromBinary(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}));
        Assert.assertEquals(Long.valueOf(Long.MAX_VALUE), attribute.dataFromBinary(new byte[]{0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}));
        Assert.assertEquals(Long.valueOf(Long.MIN_VALUE), attribute.dataFromBinary(new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}));
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
        LongAttribute attribute = new LongAttribute();
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, attribute.dataToBinary(Long.valueOf("0")));
        Assert.assertArrayEquals(new byte[]{0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, attribute.dataToBinary(Long.valueOf(Long.MAX_VALUE)));
        Assert.assertArrayEquals(new byte[]{(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, attribute.dataToBinary(Long.valueOf(Long.MIN_VALUE)));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (IllegalArgumentException e) {
            re = e;
        }
        Assert.assertEquals(IllegalArgumentException.class, re.getClass());
    }
}
