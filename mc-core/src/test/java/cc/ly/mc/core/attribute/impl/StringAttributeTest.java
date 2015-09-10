package cc.ly.mc.core.attribute.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class StringAttributeTest {

    @Test
    public void decode() {
        StringAttribute attribute = new StringAttribute();
        Assert.assertEquals("Aa", attribute.dataFromBinary(new byte[]{0x41,0x61}));
    }

    @Test
    public void encode() {
        StringAttribute attribute = new StringAttribute();
        Assert.assertArrayEquals(new byte[]{0x41,0x61}, attribute.dataToBinary("Aa"));
    }
}
