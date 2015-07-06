package cc.ly.mc.core;

import cc.ly.mc.core.attribute.impl.AttributeTest;
import cc.ly.mc.core.data.impl.DataTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ DataTest.class, AttributeTest.class })
public class CoreTest {

}
