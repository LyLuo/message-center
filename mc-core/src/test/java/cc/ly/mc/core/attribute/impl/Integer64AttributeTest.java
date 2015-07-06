package cc.ly.mc.core.attribute.impl;

import static org.junit.Assert.assertEquals;

import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.data.impl.Unsigned32;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.junit.Test;

import cc.ly.mc.core.data.impl.Integer64;

public class Integer64AttributeTest {
	@Test
	public void decode() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeShort(1);
		buffer.writeInt(8);
		buffer.writeLong(8);
		Integer64Attribute i = new Integer64Attribute();
		i.code(Unsigned16.get(buffer.readUnsignedShort()));
		i.fromBinary(buffer);
		assertEquals(Unsigned32.get(8L), i.length());
		assertEquals(Integer64.get(8L), i.data());
		assertEquals(Unsigned16.get(1), i.code());
	}
	
	@Test
	public void encode() {
		Integer64Attribute i = new Integer64Attribute();
		i.code(Unsigned16.get(1));
		i.data(new Integer64());
		ByteBuf buffer = i.toBinary();
		assertEquals(i.code().get().intValue(), buffer.readUnsignedShort());
		assertEquals(i.length().get().longValue(), buffer.readUnsignedInt());
		assertEquals(i.data().get().longValue(), buffer.readLong());
	}
}
