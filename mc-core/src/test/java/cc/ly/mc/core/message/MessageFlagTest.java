package cc.ly.mc.core.message;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ly on 9/16/15.
 */
public class MessageFlagTest {

    @Test
    public void test(){
        Assert.assertEquals(true, MessageFlag.REQUEST.isRequest());
        Assert.assertEquals(false, MessageFlag.REQUEST.isProxiable());
        Assert.assertEquals(false, MessageFlag.REQUEST.isGroup());
        Assert.assertEquals(false, MessageFlag.REQUEST.isError());
        Assert.assertEquals(true, MessageFlag.PROXIABLE_REQUEST.isRequest());
        Assert.assertEquals(true, MessageFlag.PROXIABLE_REQUEST.isProxiable());
        Assert.assertEquals(false, MessageFlag.PROXIABLE_REQUEST.isGroup());
        Assert.assertEquals(false, MessageFlag.PROXIABLE_REQUEST.isError());
        Assert.assertEquals(true, MessageFlag.GROUP_PROXIABLE_REQUEST.isRequest());
        Assert.assertEquals(true, MessageFlag.GROUP_PROXIABLE_REQUEST.isProxiable());
        Assert.assertEquals(true, MessageFlag.GROUP_PROXIABLE_REQUEST.isGroup());
        Assert.assertEquals(false, MessageFlag.GROUP_PROXIABLE_REQUEST.isError());
        Assert.assertEquals(false, MessageFlag.ANSWER.isRequest());
        Assert.assertEquals(false, MessageFlag.ANSWER.isProxiable());
        Assert.assertEquals(false, MessageFlag.ANSWER.isGroup());
        Assert.assertEquals(false, MessageFlag.ANSWER.isError());
        Assert.assertEquals(false, MessageFlag.ERROR_ANSWER.isRequest());
        Assert.assertEquals(false, MessageFlag.ERROR_ANSWER.isProxiable());
        Assert.assertEquals(false, MessageFlag.ERROR_ANSWER.isGroup());
        Assert.assertEquals(true, MessageFlag.ERROR_ANSWER.isError());
        Assert.assertEquals(false, MessageFlag.PROXIABLE_ANSWER.isRequest());
        Assert.assertEquals(true, MessageFlag.PROXIABLE_ANSWER.isProxiable());
        Assert.assertEquals(false, MessageFlag.PROXIABLE_ANSWER.isGroup());
        Assert.assertEquals(false, MessageFlag.PROXIABLE_ANSWER.isError());
        Assert.assertEquals(false, MessageFlag.ERROR_PROXIABLE_ANSWER.isRequest());
        Assert.assertEquals(true, MessageFlag.ERROR_PROXIABLE_ANSWER.isProxiable());
        Assert.assertEquals(false, MessageFlag.ERROR_PROXIABLE_ANSWER.isGroup());
        Assert.assertEquals(true, MessageFlag.ERROR_PROXIABLE_ANSWER.isError());
        Assert.assertEquals(false, MessageFlag.GROUP_PROXIABLE_ANSWER.isRequest());
        Assert.assertEquals(true, MessageFlag.GROUP_PROXIABLE_ANSWER.isProxiable());
        Assert.assertEquals(true, MessageFlag.GROUP_PROXIABLE_ANSWER.isGroup());
        Assert.assertEquals(false, MessageFlag.GROUP_PROXIABLE_ANSWER.isError());
        Assert.assertEquals(false, MessageFlag.ERROR_GROUP_PROXIABLE_ANSWER.isRequest());
        Assert.assertEquals(true, MessageFlag.ERROR_GROUP_PROXIABLE_ANSWER.isProxiable());
        Assert.assertEquals(true, MessageFlag.ERROR_GROUP_PROXIABLE_ANSWER.isGroup());
        Assert.assertEquals(true, MessageFlag.ERROR_GROUP_PROXIABLE_ANSWER.isError());
        Assert.assertEquals(MessageFlag.ANSWER, MessageFlag.REQUEST.requestToAnswer(false));
        Assert.assertEquals(MessageFlag.ERROR_ANSWER, MessageFlag.REQUEST.requestToAnswer(true));
        Assert.assertEquals(MessageFlag.PROXIABLE_ANSWER, MessageFlag.PROXIABLE_REQUEST.requestToAnswer(false));
        Assert.assertEquals(MessageFlag.ERROR_PROXIABLE_ANSWER, MessageFlag.PROXIABLE_REQUEST.requestToAnswer(true));
        Assert.assertEquals(MessageFlag.GROUP_PROXIABLE_ANSWER, MessageFlag.GROUP_PROXIABLE_REQUEST.requestToAnswer(false));
        Assert.assertEquals(MessageFlag.ERROR_GROUP_PROXIABLE_ANSWER, MessageFlag.GROUP_PROXIABLE_REQUEST.requestToAnswer(true));
        Exception actual = null;
        try {
            MessageFlag.ANSWER.requestToAnswer(true);
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(IllegalStateException.class, actual.getClass());
        try {
            MessageFlag.fromBinary((byte) 0x11111111);
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(IllegalArgumentException.class, actual.getClass());
    }
}
