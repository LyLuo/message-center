package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class UnsignedIntAttributeTest {

    @Test
    public void decode() {
        UnsignedIntAttribute attribute = new UnsignedIntAttribute();
        Assert.assertEquals(Long.valueOf(0), attribute.dataFromBinary(new byte[]{0, 0, 0, 0}));
        Assert.assertEquals(Long.valueOf(0xFFFFFFFFL), attribute.dataFromBinary(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}));
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
        UnsignedIntAttribute attribute = new UnsignedIntAttribute();
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0}, attribute.dataToBinary(0L));
        Assert.assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, attribute.dataToBinary(0xFFFFFFFFL));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (NullPointerException e) {
            re = e;
        }
        Assert.assertEquals(NullPointerException.class, re.getClass());
    }
}
