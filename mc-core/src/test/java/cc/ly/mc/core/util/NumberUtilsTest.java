package cc.ly.mc.core.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/10/15.
 */
public class NumberUtilsTest {
    @Test
    public void bytes6ToLongTest() {
        Assert.assertEquals(0L, NumberUtils.bytes6ToLong(new byte[]{0,0,0,0,0,0}));
        Assert.assertEquals(0xFFFFFFFFFFFFL, NumberUtils.bytes6ToLong(new byte[]{(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff}));
    }

    @Test
    public void longToBytes6Test() {
        Assert.assertArrayEquals(new byte[]{0,0,0,0,0,0}, NumberUtils.longToBytes6(0));
        Assert.assertArrayEquals(new byte[]{(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff}, NumberUtils.longToBytes6(0xFFFFFFFFFFFFL));
    }
}
