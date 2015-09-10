package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class ByteAttributeTest {

    @Test
    public void decode() {
        ByteAttribute attribute = new ByteAttribute();
        Assert.assertEquals(Byte.valueOf("0"), attribute.dataFromBinary(new byte[]{0}));
        Assert.assertEquals(Byte.valueOf("-128"), attribute.dataFromBinary(new byte[]{-128}));
        Assert.assertEquals(Byte.valueOf("127"), attribute.dataFromBinary(new byte[]{127}));
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
        ByteAttribute attribute = new ByteAttribute();
        Assert.assertArrayEquals(new byte[]{0}, attribute.dataToBinary(Byte.valueOf("0")));
        Assert.assertArrayEquals(new byte[]{127}, attribute.dataToBinary(Byte.valueOf("127")));
        Assert.assertArrayEquals(new byte[]{-128}, attribute.dataToBinary(Byte.valueOf("-128")));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (IllegalArgumentException e) {
            re = e;
        }
        Assert.assertEquals(IllegalArgumentException.class, re.getClass());
    }
}
