package cc.ly.mc.core.data.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Unsigned32Test {

	@Test
	public void test() {
		Unsigned32 u0 = Unsigned32.UNSIGNED32_0;
		assertEquals(u0, Unsigned32.get(0L));
	}
}
