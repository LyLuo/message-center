package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.data.impl.Unsigned16;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static io.netty.util.CharsetUtil.UTF_8;
import static org.junit.Assert.assertEquals;

public class BoolAttributeTest {
	@Test
	public void test() {
		BoolAttribute accept = new BoolAttribute();
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeShort(1);
		buffer.writeInt(1);
		buffer.writeBoolean(false);
		Unsigned16 code = new Unsigned16();
		code.fromBinary(buffer);
		accept.code(code);
		accept.fromBinary(buffer);
		assertEquals(1, accept.length().get().intValue());
		assertEquals(1, accept.code().get().intValue());
		assertEquals(false, accept.data().get());
	}
}