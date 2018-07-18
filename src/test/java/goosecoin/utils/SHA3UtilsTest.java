package goosecoin.utils;

import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;

import static org.junit.jupiter.api.Assertions.*;

class SHA3UtilsTest {

    @Test
    void defaultStringDigest() {
       byte[] digest = SHA3Utils.digest("Hello World!");
       assertNotNull(digest);
       final byte[] HELLO_WORLD_HASH = DatatypeConverter.parseHexBinary("716596AFADFA17CD1CB35133829A02B03E4EED398CE029CE78A2161D");
       assertArrayEquals(HELLO_WORLD_HASH, digest);
    }

    @Test
    void defaultBytesDigest() {
        final byte[] INPUT = DatatypeConverter.parseHexBinary("00");
        final byte[] EXPECTED_HASH = DatatypeConverter.parseHexBinary("BDD5167212D2DC69665F5A8875AB87F23D5CE7849132F56371A19096");
        byte[] digest = SHA3Utils.digest(INPUT);
        assertNotNull(digest);
        assertArrayEquals(EXPECTED_HASH, digest);
    }

    @Test
    void stringDigest() {
        byte[] digest = SHA3Utils.digest("Hello World!", SHA3Utils.Size.S224);
        assertNotNull(digest);
        final byte[] HELLO_WORLD_HASH = DatatypeConverter.parseHexBinary("716596AFADFA17CD1CB35133829A02B03E4EED398CE029CE78A2161D");
        assertArrayEquals(HELLO_WORLD_HASH, digest);
    }

    @Test
    void bytesDigest() {
        final byte[] INPUT = DatatypeConverter.parseHexBinary("00");
        final byte[] EXPECTED_HASH = DatatypeConverter.parseHexBinary("BDD5167212D2DC69665F5A8875AB87F23D5CE7849132F56371A19096");
        byte[] digest = SHA3Utils.digest(INPUT, SHA3Utils.Size.S224);
        assertNotNull(digest);
        assertArrayEquals(EXPECTED_HASH, digest);
    }
}