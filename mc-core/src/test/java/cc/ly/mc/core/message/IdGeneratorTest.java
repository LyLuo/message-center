package cc.ly.mc.core.message;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/16/15.
 */
public class IdGeneratorTest {
    @Test
    public void test(){
        for(int i = 0; i < 99; i++) {
            IdGenerator.nextHopByHop();
        }
        Assert.assertEquals(100,IdGenerator.nextHopByHop());
        for(int i = 0; i < 99; i++) {
            IdGenerator.nextEndToEnd();
        }
        Assert.assertEquals(100,IdGenerator.nextEndToEnd());
    }
}
