package cc.ly.mc.core.attribute.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ Integer32AttributeTest.class,
		Integer64AttributeTest.class, Unsigned32AttributeTest.class,
		Unsigned64AttributeTest.class, UTF8AttributeTest.class,
		BoolAttributeTest.class})
public class AttributeTest {

}
