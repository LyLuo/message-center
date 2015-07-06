package cc.ly.mc.core.util;

public class NumberUtils {

	public static byte[] unsigned64ToBytes(long value) {
		return toBytes(value, 8);
	}

	public static byte[] unsigned48ToBytes(long value) {
		return toBytes(value, 6);
	}

	public static byte[] unsigned32ToBytes(long value) {
		return toBytes(value, 4);
	}

	public static byte[] unsigned16ToBytes(int value) {
		return toBytes(value, 2);
	}

	public static byte[] unsigned8ToBytes(int value) {
		return toBytes(value, 1);
	}

	public static byte[] toBytes(long value, int len) {
		byte[] array = new byte[len];
		for (int i = 0; i < len; i++) {
			array[i] = (byte) (value >>> ((len - i - 1) * 8));
		}
		return array;
	}

	public static long fromBytes(byte[] bytes, int len) {
		long v = 0;
		for (int i = 0; i < len; i++) {
			v += (long) (bytes[i] & 255) << (len - i - 1) * 8;
		}
		return v;
	}

	public static short unsigned8ToShort(byte[] bytes) {
		return (short) fromBytes(bytes, 1);
	}

	public static int unsigned16ToInt(byte[] bytes) {
		return (int) fromBytes(bytes, 2);
	}

	public static int unsigned24ToInt(byte[] bytes) {
		return (int) fromBytes(bytes, 3);
	}

	public static long unsigned32ToInt(byte[] bytes) {
		return fromBytes(bytes, 4);
	}

	public static long unsigned48ToLong(byte[] bytes) {
		return fromBytes(bytes, 6);
	}

	public static long from64(byte[] bytes) {
		return fromBytes(bytes, 8);
	}

}
