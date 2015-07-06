package cc.ly.mc.core.attribute.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.data.impl.Unsigned64;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.junit.Assert;
import org.junit.Test;

public class Unsigned64AttributeTest {
	@Test
	public void decode() {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeShort(1);
		buffer.writeInt(8);
		buffer.writeLong(8);
		Unsigned64Attribute u = new Unsigned64Attribute();
		u.code(Unsigned16.get(buffer.readUnsignedShort()));
		u.fromBinary(buffer);
		Assert.assertEquals(Unsigned32.get(8L), u.length());
		Assert.assertEquals(Unsigned64.get(new BigInteger("8")), u.data());
		Assert.assertEquals(Unsigned16.get(1), u.code());
	}
	
	@Test
	public void encode() {
		Unsigned64Attribute u = new Unsigned64Attribute();
		u.code(Unsigned16.get(1));
		u.data(new Unsigned64());
		ByteBuf buffer = u.toBinary();
		Assert.assertEquals(u.code().get().intValue(), buffer.readUnsignedShort());
		Assert.assertEquals(u.length().get().longValue(), buffer.readUnsignedInt());
		Assert.assertEquals(u.data().get().longValue(), buffer.readLong());
	}
}
