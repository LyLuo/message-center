package cc.ly.mc.core.attribute.impl;

import static org.junit.Assert.assertEquals;

import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.data.impl.Unsigned32;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.junit.Assert;
import org.junit.Test;

public class Unsigned32AttributeTest {
	@Test
	public void decode() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeShort(1);
		buffer.writeInt(4);
		buffer.writeInt(8);
		Unsigned16 code = new Unsigned16();
		code.fromBinary(buffer);
		Unsigned32Attribute i = new Unsigned32Attribute();
		i.code(code);
		i.fromBinary(buffer);
		Assert.assertEquals(4, i.length().get().intValue());
		Assert.assertEquals(8, i.data().get().intValue());
		Assert.assertEquals(1, i.code().get().intValue());
	}
	
	@Test
	public void encode() {
		Unsigned32Attribute i = new Unsigned32Attribute();
		i.code(Unsigned16.get(1));
		i.data(new Unsigned32());
		ByteBuf buffer = i.toBinary();
		Assert.assertEquals(i.code().get().intValue(), buffer.readUnsignedShort());
		Assert.assertEquals(i.length().get().longValue(), buffer.readUnsignedInt());
		Assert.assertEquals(i.data().get().intValue(), buffer.readInt());
	}
}
