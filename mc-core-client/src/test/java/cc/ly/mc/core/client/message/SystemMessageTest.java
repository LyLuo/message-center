package cc.ly.mc.core.client.message;

import static org.junit.Assert.assertEquals;

import cc.ly.mc.core.attribute.impl.UTF8Attribute;
import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.FlagImpl;
import cc.ly.mc.core.data.impl.UTF8;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.message.GenericMessage;
import cc.ly.mc.core.message.MessageFactory;
import io.netty.buffer.ByteBuf;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.data.impl.Unsigned32;

public class SystemMessageTest {

	public static class MockGenericSystemMessage extends GenericMessage {

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
	}

	@Test
	public void testEncode() {
		GenericMessage message = new MockGenericSystemMessage();
		message.length(Unsigned32.get(30L));
		message.flag(new FlagData(FlagImpl.REQUEST));
		message.hopByHop(Integer64.get(1L));
		message.endToEnd(Integer64.get(2L));
		UTF8Attribute attr1 = new UTF8Attribute(new UTF8("A"));
		attr1.code(Unsigned16.get(1));
		message.addAttribute(attr1);
		UTF8Attribute attr2 = new UTF8Attribute(new UTF8("B"));
		attr2.code(Unsigned16.get(2));
		message.addAttribute(attr2);
		ByteBuf buf = MessageFactory.getInstance().generateByteBuf(message);
		assertEquals(message.version().get().byteValue(),
				buf.readUnsignedByte());
		Assert.assertEquals(message.code().get().intValue(), buf.readUnsignedShort());
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
