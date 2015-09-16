package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class ByteArrayAttributeTest {

    @Test
    public void decode() {
        ByteArrayAttribute attribute = new ByteArrayAttribute();
        Assert.assertArrayEquals(new byte[]{0}, attribute.dataFromBinary(new byte[]{0}));
        Assert.assertArrayEquals(new byte[]{0,0,0,0}, attribute.dataFromBinary(new byte[]{0,0,0,0}));
        RuntimeException re = null;
        try {
            attribute.dataFromBinary(null);
        } catch (NullPointerException e) {
            re = e;
        }
        Assert.assertEquals(NullPointerException.class, re.getClass());
    }

    @Test
    public void encode() {
        ByteArrayAttribute attribute = new ByteArrayAttribute();
        Assert.assertArrayEquals(new byte[]{0}, attribute.dataToBinary(new byte[]{0}));
        Assert.assertArrayEquals(new byte[]{0,0,0,0}, attribute.dataToBinary(new byte[]{0,0,0,0}));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (NullPointerException e) {
            re = e;
        }
        Assert.assertEquals(NullPointerException.class, re.getClass());
    }
}
