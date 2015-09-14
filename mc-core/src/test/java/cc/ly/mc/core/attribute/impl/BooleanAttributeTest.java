package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class BooleanAttributeTest {

    @Test
    public void decode() {
        BooleanAttribute attribute = new BooleanAttribute();
        Assert.assertEquals(false, attribute.dataFromBinary(new byte[]{0}));
        Assert.assertEquals(true, attribute.dataFromBinary(new byte[]{1}));
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
        BooleanAttribute attribute = new BooleanAttribute();
        Assert.assertArrayEquals(new byte[]{1}, attribute.dataToBinary(true));
        Assert.assertArrayEquals(new byte[]{0}, attribute.dataToBinary(false));
        RuntimeException re = null;
        try {
            attribute.dataToBinary(null);
        } catch (NullPointerException e) {
            re = e;
        }
        Assert.assertEquals(NullPointerException.class, re.getClass());
    }
}
