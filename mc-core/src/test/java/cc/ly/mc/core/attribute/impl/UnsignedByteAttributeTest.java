package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class UnsignedByteAttributeTest {

    @Test
    public void decode() {
        UnsignedByteAttribute attribute = new UnsignedByteAttribute();
        Assert.assertEquals(Short.valueOf("0"), attribute.dataFromBinary(new byte[]{0}));
        Assert.assertEquals(Short.valueOf((short) 0xFF), attribute.dataFromBinary(new byte[]{-1}));
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
        UnsignedByteAttribute attribute = new UnsignedByteAttribute();
        Assert.assertArrayEquals(new byte[]{0}, attribute.dataToBinary(Short.valueOf("0")));
        Assert.assertArrayEquals(new byte[]{-1}, attribute.dataToBinary((short) 0xFF));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (NullPointerException e) {
            re = e;
        }
        Assert.assertEquals(NullPointerException.class, re.getClass());
    }
}
