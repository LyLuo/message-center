package cc.ly.mc.core.attribute.impl;

import static org.junit.Assert.assertEquals;

import cc.ly.mc.core.data.impl.Unsigned16;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import static io.netty.util.CharsetUtil.UTF_8;

import org.junit.Test;

public class UTF8AttributeTest {
	@Test
	public void test() {
		UTF8Attribute name = new UTF8Attribute();
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeShort(1);
		buffer.writeInt(12);
		buffer.writeBytes("abcdef中国".getBytes(UTF_8));
		Unsigned16 code = new Unsigned16();
		code.fromBinary(buffer);
		name.code(code);
		name.fromBinary(buffer);
		assertEquals(12, name.length().get().intValue());
		assertEquals("abcdef中国", name.data().get());
		assertEquals(1, name.code().get().intValue());
	}
}