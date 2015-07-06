package cc.ly.mc.core.server.message;

import static org.junit.Assert.assertEquals;

import cc.ly.mc.core.attribute.AttributeFactory;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.message.MessageFactory;
import cc.ly.mc.core.data.impl.FlagImpl;
import cc.ly.mc.core.data.impl.UTF8;
import cc.ly.mc.core.data.impl.Unsigned32;
import cc.ly.mc.core.message.GenericMessage;
import cc.ly.mc.core.message.SystemMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cc.ly.mc.core.attribute.impl.UTF8Attribute;
import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.Integer64;

public class SystemMessageTest {

	public static class MockGenericSystemMessage extends SystemMessage {

		private static final long serialVersionUID = 8780602667145271121L;

		public static final Unsigned16 CODE = Unsigned16.get(1);

		public MockGenericSystemMessage() {
			code = CODE;
		}

		@Override
		public void onReceived() {
		}

	}

	@BeforeClass
	public static void before() {
		MessageFactory.getInstance().register(MockGenericSystemMessage.class);
		AttributeFactory.getInstance().register(Unsigned16.get(3), UTF8Attribute.class);
		AttributeFactory.getInstance().register(Unsigned16.get(5), UTF8Attribute.class);
	}

	@Test
	public void testDecode() {
		ByteBuf buffer = Unpooled.buffer();
		// header
		buffer.writeByte(0);
		buffer.writeShort(1);
		buffer.writeByte(Integer.valueOf(240).byteValue());
		buffer.writeInt(42);
		buffer.writeLong(1);
		buffer.writeLong(2);
		// attributes
		buffer.writeShort(3);
		buffer.writeInt(3);
		buffer.writeBytes("luo".getBytes());
		buffer.writeShort(5);
		buffer.writeInt(3);
		buffer.writeBytes("luo".getBytes());
		MockGenericSystemMessage message = (MockGenericSystemMessage) MessageFactory
				.getInstance().createMessage(buffer);
		assertEquals(message.version(), GenericMessage.VERSION);
		Assert.assertEquals(message.length(), Unsigned32.get(42L));
		assertEquals(message.flag().get().isGroup(), true);
		assertEquals(message.flag().get().isRequest(), true);
		assertEquals(message.flag().get().isProxiable(), true);
		assertEquals(message.flag().get().isError(), true);
		assertEquals(message.code(), Unsigned16.get(1));
		assertEquals(message.hopByHop(), Integer64.get(1L));
		assertEquals(message.endToEnd(), Integer64.get(2L));
		assertEquals(message.attribute(Unsigned16.get(3)).data().get(), "luo");
		assertEquals(message.attribute(Unsigned16.get(5)).data().get(), "luo");
	}

	@Test
	public void testEncode() {
		SystemMessage message = new MockGenericSystemMessage();
		message.flag(new FlagData(FlagImpl.REQUEST));
		message.hopByHop(Integer64.get(1L));
		message.endToEnd(Integer64.get(2L));
		UTF8Attribute attr1 = new UTF8Attribute(new UTF8("A"));
		attr1.code(Unsigned16.get(1));
		message.addAttribute(attr1);
		UTF8Attribute attr2 = new UTF8Attribute(new UTF8("B"));
		attr2.code(Unsigned16.get(2));
		message.addAttribute(attr2);
		ByteBuf buf = MessageFactory.getInstance().generateByteBuf(
				message);
		assertEquals(message.version().get().byteValue(),
				buf.readUnsignedByte());
		assertEquals(message.code().get().intValue(), buf.readUnsignedShort());
		assertEquals(64, buf.readUnsignedByte());
		assertEquals(message.length().get().intValue(), buf.readUnsignedInt());
		assertEquals(message.hopByHop().get().longValue(), buf.readLong());
		assertEquals(message.endToEnd().get().longValue(), buf.readLong());
		assertEquals(1, buf.readUnsignedShort());
		assertEquals(1, buf.readUnsignedInt());
		assertEquals(0x41, buf.readByte());
		assertEquals(2, buf.readUnsignedShort());
		assertEquals(1, buf.readUnsignedInt());
		assertEquals(0x42, buf.readByte());
	}
}
