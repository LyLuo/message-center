package cc.ly.mc.core.event;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ly on 9/16/15.
 */
public class EventTest {

    @Test
    public void test() {
        Dummy dummy = new Dummy();
        EventBus.getInstance().register("test", dummy);
        EventBus.getInstance().notify("test", new Object());
        EventBus.getInstance().deregister("test", dummy);
        Assert.assertEquals(0, EventBus.getInstance().deregister("test").size());
        CountDownLatch count = new CountDownLatch(2);
        new Thread(){
            public void run(){
                int i = 0;
                while(i < 100) {
                    EventBus.getInstance().register("concurrent", dummy);
                    i++;
                }
                count.countDown();
            }
        }.start();
        new Thread() {
            public void run() {
                int i = 0;
                while(i < 100) {
                    EventBus.getInstance().register("concurrent", dummy);
                    i++;
                }
                count.countDown();
            }
        }.start();
        Exception actual = null;
        try {
            EventBus.getInstance().register(null, null);
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(NullPointerException.class, actual.getClass());
        try {
            EventBus.getInstance().deregister(null);
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(NullPointerException.class, actual.getClass());
        try {
            EventBus.getInstance().deregister(null, null);
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(NullPointerException.class, actual.getClass());
        try {
            EventBus.getInstance().notify(null, null);
        } catch (Exception e) {
            actual = e;
        }
        Assert.assertNotNull(actual);
        Assert.assertEquals(NullPointerException.class, actual.getClass());
        try {
            count.await();
            Assert.assertEquals(200, EventBus.getInstance().deregister("concurrent").size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Dummy implements EventObserver {
        @Override
        public void update(Object source) {

        }
    }
}
