package cc.ly.mc.core.data.impl;

import static org.junit.Assert.assertEquals;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.junit.Test;

public class FlagImpTest {

	@Test
	public void testEncode() {
		ByteBuf buffer = FlagImpl.DUMMY.toBinary();
		assertEquals(0b11110000, buffer.readUnsignedByte());
		buffer = FlagImpl.PROXY_GROUP_REQUEST.toBinary();
		assertEquals(0b11100000, buffer.readUnsignedByte());
		buffer = FlagImpl.PROXY_GROUP_ANSWER.toBinary();
		assertEquals(0b10100000, buffer.readUnsignedByte());
		buffer = FlagImpl.PROXY_REQUEST.toBinary();
		assertEquals(0b01100000, buffer.readUnsignedByte());
		buffer = FlagImpl.PROXY_ANSWER.toBinary();
		assertEquals(0b00100000, buffer.readUnsignedByte());
		buffer = FlagImpl.REQUEST.toBinary();
		assertEquals(0b01000000, buffer.readUnsignedByte());
		buffer = FlagImpl.GROUP_REQUEST.toBinary();
		assertEquals(0b11000000, buffer.readUnsignedByte());
		buffer = FlagImpl.GROUP_ANSWER.toBinary();
		assertEquals(0b10000000, buffer.readUnsignedByte());
		buffer = FlagImpl.ANSWER.toBinary();
		assertEquals(0, buffer.readUnsignedByte());
		buffer = FlagImpl.ERROR.toBinary();
		assertEquals(0b00010000, buffer.readUnsignedByte());
	}

	@Test
	public void testDecode() {
		FlagImpl f = FlagImpl.DUMMY;
		ByteBuf buffer = Unpooled
				.copiedBuffer(new byte[] { (byte) 0b11110000 });
		f.fromBinary(buffer);
		assertEquals(f.isGroup(), true);
		assertEquals(f.isRequest(), true);
		assertEquals(f.isProxiable(), true);
		assertEquals(f.isError(), true);
		f = FlagImpl.PROXY_GROUP_REQUEST;
		buffer = Unpooled.copiedBuffer(new byte[] { (byte) 0b11100000 });
		f.fromBinary(buffer);
		assertEquals(f.isGroup(), true);
		assertEquals(f.isRequest(), true);
		assertEquals(f.isProxiable(), true);
		assertEquals(f.isError(), false);
		f = FlagImpl.GROUP_REQUEST;
		buffer = Unpooled.copiedBuffer(new byte[] { (byte) 0b11000000 });
		f.fromBinary(buffer);
		assertEquals(f.isGroup(), true);
		assertEquals(f.isRequest(), true);
		assertEquals(f.isProxiable(), false);
		assertEquals(f.isError(), false);
		f = FlagImpl.PROXY_GROUP_ANSWER;
		buffer = Unpooled.copiedBuffer(new byte[] { (byte) 0b10100000 });
		f.fromBinary(buffer);
		assertEquals(f.isGroup(), true);
		assertEquals(f.isRequest(), false);
		assertEquals(f.isProxiable(), true);
		assertEquals(f.isError(), false);
		f = FlagImpl.GROUP_ANSWER;
		buffer = Unpooled.copiedBuffer(new byte[] { (byte) 0b10000000 });
		f.fromBinary(buffer);
		assertEquals(f.isGroup(), true);
		assertEquals(f.isRequest(), false);
		assertEquals(f.isProxiable(), false);
		assertEquals(f.isError(), false);
		f = FlagImpl.PROXY_REQUEST;
		buffer = Unpooled.copiedBuffer(new byte[] { (byte) 0b1100000 });
		f.fromBinary(buffer);
		assertEquals(f.isGroup(), false);
		assertEquals(f.isRequest(), true);
		assertEquals(f.isProxiable(), true);
		assertEquals(f.isError(), false);
		f = FlagImpl.REQUEST;
		buffer = Unpooled.copiedBuffer(new byte[] { (byte) 0b1000000 });
		f.fromBinary(buffer);
		assertEquals(f.isGroup(), false);
		assertEquals(f.isRequest(), true);
		assertEquals(f.isProxiable(), false);
		assertEquals(f.isError(), false);
		f = FlagImpl.PROXY_ANSWER;
		buffer = Unpooled.copiedBuffer(new byte[] { (byte) 0b100000 });
		f.fromBinary(buffer);
		assertEquals(f.isGroup(), false);
		assertEquals(f.isRequest(), false);
		assertEquals(f.isProxiable(), true);
		assertEquals(f.isError(), false);
		f = FlagImpl.ANSWER;
		buffer = Unpooled.copiedBuffer(new byte[] { (byte) 0 });
		f.fromBinary(buffer);
		assertEquals(f.isGroup(), false);
		assertEquals(f.isRequest(), false);
		assertEquals(f.isProxiable(), false);
		assertEquals(f.isError(), false);
	}

}
