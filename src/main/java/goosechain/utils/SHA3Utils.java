package goosechain.utils;

import goosechain.crypto.digests.SHA3Digest;

import java.io.UnsupportedEncodingException;

public class SHA3Utils {

    protected enum Size {

        S224(224),
        S256(256),
        S384(384),
        S512(512);

        private int bits;

        Size(int bits) {
            this.bits = bits;
        }

        public int getValue() {
            return this.bits;
        }
    }

    private static Size DEFAULT = Size.S224;

    public static byte[] digest(String input) {
        return digest(input, DEFAULT);
    }

    public static byte[] digest(byte[] input) {
        return digest(input, DEFAULT);
    }

    public static byte[] digest(String string, Size s) {
        Size size = s == null ? DEFAULT : s;

        String text = string != null ? string : "null";
        try {
            return digest(text.getBytes("UTF-8"), s);
        } catch (UnsupportedEncodingException ex) {
            // most unlikely
            return digest(text.getBytes(), s);
        }
    }

    public static byte[] digest(byte[] input, Size s) {
        SHA3Digest md = new SHA3Digest(s.getValue());
        byte[] output = new byte[s.getValue() / 8];
        md.update(input, 0, input.length);
        md.doFinal(output, 0);
        return output;
    }

}
