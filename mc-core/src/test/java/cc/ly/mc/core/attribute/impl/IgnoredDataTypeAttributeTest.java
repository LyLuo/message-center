package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class IgnoredDataTypeAttributeTest {

    @Test
    public void decode() {
        IgnoredDataTypeAttribute attribute = new IgnoredDataTypeAttribute();
        Assert.assertArrayEquals(new byte[]{0}, attribute.dataFromBinary(new byte[]{0}));
        Assert.assertArrayEquals(new byte[]{0,0,0,0}, attribute.dataFromBinary(new byte[]{0,0,0,0}));
    }

    @Test
    public void encode() {
        IgnoredDataTypeAttribute attribute = new IgnoredDataTypeAttribute();
        Assert.assertArrayEquals(new byte[]{0}, attribute.dataToBinary(new byte[]{0}));
        Assert.assertArrayEquals(new byte[]{0,0,0,0}, attribute.dataToBinary(new byte[]{0,0,0,0}));
    }
}
