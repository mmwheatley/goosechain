package goosechain.utils;

/**
 * Wrote my own hex string encoder because I want to preserve
 * all the leading zeros in the hex representation of the byte array
 * and I couldn't find a lib that did that
 */
public class Hex {

    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    private Hex() {}

    public static String toHexString(byte[] input) {
        StringBuilder sb = new StringBuilder();

        for(byte b: input) {
            sb.append(HEX_CHARS[b>>4 & 0xF]);
            sb.append(HEX_CHARS[b & 0xF]);
        }
        return sb.toString();
    }
}
