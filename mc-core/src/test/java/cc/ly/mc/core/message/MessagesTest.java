package cc.ly.mc.core.message;

import org.junit.Assert;
import org.junit.Test;

import static cc.ly.mc.core.message.Messages.*;

/**
 * Created by ly on 9/16/15.
 */
public class MessagesTest {
    @Test
    public void test() {
        Assert.assertEquals(1, VERSION_FIELD_LENGTH);
        Assert.assertEquals(3, LENGTH_FIELD_LENGTH);
        Assert.assertEquals(4, VERSION_LENGTH_FIELD_LENGTH);
        Assert.assertEquals(1, FLAG_FIELD_LENGTH);
        Assert.assertEquals(3, CODE_FIELD_LENGTH);
        Assert.assertEquals(4, HOP_BY_HOP_FIELD_LENGTH);
        Assert.assertEquals(4, END_TO_END_FIELD_LENGTH);
        Assert.assertEquals(16, MESSAGE_FIELDS_LENGTH);
    }
}
