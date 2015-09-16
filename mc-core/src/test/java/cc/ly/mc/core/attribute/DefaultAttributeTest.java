package cc.ly.mc.core.attribute;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/15/15.
 */
public class DefaultAttributeTest {
    @Test
    public void testCreate(){
        Exception actual = null;
        try {
            new DefaultAttribute(0, AttributeFlag.BOOLEAN, 0, null) {
                @Override
                public Object dataFromBinary(byte[] payload) {
                    return null;
                }

                @Override
                public byte[] dataToBinary(Object o) {
                    return new byte[0];
                }
            };
        }catch (Exception e){
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(IllegalArgumentException.class,actual.getClass());
    }
}
