package cc.ly.mc.core.server.message;

import static org.junit.Assert.assertEquals;

import cc.ly.mc.core.attribute.AttributeFactory;
import cc.ly.mc.core.attribute.impl.IgnoredAttribute;
import cc.ly.mc.core.attribute.impl.UTF8Attribute;
import cc.ly.mc.core.message.GenericMessage;
import cc.ly.mc.core.message.MessageFactory;
import cc.ly.mc.core.message.RelayMessage;
import cc.ly.mc.core.data.impl.FlagImpl;
import cc.ly.mc.core.data.impl.Unsigned32;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.IgnoredData;
import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.data.impl.Unsigned16;

public class RelayMessageTest {

	public static class MockRelayMessage extends RelayMessage {

		private static final long serialVersionUID = 8780602667145271121L;

		public static final Unsigned16 CODE = Unsigned16.get(1);

		public MockRelayMessage() {
			code = CODE;
		}

		@Override
		public void onReceived() {
		}
	}

	@BeforeClass
	public static void before() {
		MessageFactory.getInstance().register(MockRelayMessage.class);
		AttributeFactory.getInstance().register(Unsigned16.get(4),
				UTF8Attribute.class);
		AttributeFactory.getInstance().register(Unsigned16.get(5),
				UTF8Attribute.class);
	}

	@Test
	public void testDecode() {
		ByteBuf buffer = Unpooled.buffer();
		// header
		buffer.writeByte(0);
		buffer.writeShort(1);
		buffer.writeByte(Integer.valueOf(240).byteValue());
		buffer.writeInt(44);
		buffer.writeLong(1);
		buffer.writeLong(2);
		// attributes
		buffer.writeShort(4);
		buffer.writeInt(4);
		buffer.writeBytes("luo!".getBytes());
		buffer.writeShort(5);
		buffer.writeInt(4);
		buffer.writeBytes("luo!".getBytes());
		MockRelayMessage message = (MockRelayMessage) MessageFactory
				.getInstance().createMessage(buffer);
		Assert.assertEquals(message.version(), GenericMessage.VERSION);
		Assert.assertEquals(message.length(), Unsigned32.get(44L));
		Assert.assertEquals(message.flag().get().isGroup(), true);
		Assert.assertEquals(message.flag().get().isRequest(), true);
		Assert.assertEquals(message.flag().get().isProxiable(), true);
		Assert.assertEquals(message.flag().get().isError(), true);
		assertEquals(message.code(), Unsigned16.get(1));
		assertEquals(message.hopByHop(), Integer64.get(1L));
		assertEquals(message.endToEnd(), Integer64.get(2L));
		Assert.assertEquals(message.attribute(Unsigned16.get(4)).length(),
				Unsigned32.get(4L));
		assertEquals(message.attribute(Unsigned16.get(4)).data().get(), "luo!");
		Assert.assertEquals(message.attribute(Unsigned16.get(5)).length(),
				Unsigned32.get(4L));
		assertEquals(message.attribute(Unsigned16.get(5)).data().get(), "luo!");
	}

	@Test
	public void testEncode() {
		MockRelayMessage message = new MockRelayMessage();
		message.code(Unsigned16.get(100));
		message.length(Unsigned32.get(36L));
		message.flag(new FlagData(FlagImpl.REQUEST));
		message.hopByHop(Integer64.get(1L));
		message.endToEnd(Integer64.get(2L));
		message.addAttribute(new IgnoredAttribute(new IgnoredData(new byte[] {
				1, 2, 3, 4 })));
		ByteBuf buf = MessageFactory.getInstance().generateByteBuf(message);
		Assert.assertEquals(message.version().get().byteValue(),
				buf.readUnsignedByte());
		Assert.assertEquals(message.code().get().intValue(), buf.readUnsignedShort());
		assertEquals(64, buf.readUnsignedByte());
		Assert.assertEquals(message.length().get().intValue(), buf.readUnsignedInt());
		assertEquals(message.hopByHop().get().longValue(), buf.readLong());
		assertEquals(message.endToEnd().get().longValue(), buf.readLong());
		assertEquals(0, buf.readUnsignedShort());
		assertEquals(4, buf.readUnsignedInt());
		assertEquals(1, buf.readByte());
		assertEquals(2, buf.readByte());
		assertEquals(3, buf.readByte());
		assertEquals(4, buf.readByte());
	}
}
