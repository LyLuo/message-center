package cc.ly.mc.core.util;

public class NumberUtils {

    public static byte[] unsignedLongToBytes8(String value) {
        return value.getBytes();
    }

    public static byte[] longToBytes8(long value) {
        return toBytes(value, 8);
    }

    public static byte[] longToBytes6(long value) {
        return toBytes(value, 6);
    }

    public static byte[] unsignedIntToBytes4(long value) {
        return toBytes(value, 4);
    }

    public static byte[] intToBytes4(int value) {
        return toBytes(value, 4);
    }

    public static byte[] intToBytes3(int value) {
        return toBytes(value, 3);
    }

    public static byte[] unsignedShortToBytes2(int value) {
        return toBytes(value, 2);
    }

    public static byte[] shortToBytes2(short value) {
        return toBytes(value, 2);
    }

    public static byte[] unsignedByteToBytes1(short value) {
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
            v += (long) (bytes[i] & 0xFF) << (len - i - 1) * 8;
        }
        return v;
    }

    public static short bytes1ToUnsignedByte(byte[] bytes) {
        return (short) fromBytes(bytes, 1);
    }

    public static short bytes2ToShort(byte[] bytes) {
        return (short) fromBytes(bytes, 2);
    }

    public static int bytes2ToUnsignedShort(byte[] bytes) {
        return (int) fromBytes(bytes, 2);
    }

    public static int bytes3ToInt(byte[] bytes) {
        return (int) fromBytes(bytes, 3);
    }

    public static int bytes4ToInt(byte[] bytes) {
        return (int) fromBytes(bytes, 4);
    }

    public static long bytes4ToUnsignedInt(byte[] bytes) {
        return fromBytes(bytes, 4);
    }

    public static long bytes6ToLong(byte[] bytes) {
        return fromBytes(bytes, 6);
    }

    public static long bytes8ToLong(byte[] bytes) {
        return fromBytes(bytes, 8);
    }

    public static String bytes8ToUnsignedLong(byte[] bytes) {
        return new String(bytes);
    }

}
