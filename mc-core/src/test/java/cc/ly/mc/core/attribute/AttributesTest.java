package cc.ly.mc.core.attribute;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/15/15.
 */
public class AttributesTest {

    @Test
    public void testGetLength() {
        Exception actual = null;
        try {
            Attributes.getLength(null);
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(NullPointerException.class, actual.getClass());
    }

    @Test
    public void testParse() {
        Exception actual = null;
        byte[] payload = new byte[]{0x00, 0x01, 0x01};
        try {
            Attributes.parse(payload);
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(IllegalArgumentException.class, actual.getClass());
    }
}
