package goosechain.utils;

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
        // len: 1
        final byte[] INPUT = DatatypeConverter.parseHexBinary("00");
        final byte[] EXPECTED_HASH = DatatypeConverter.parseHexBinary("BDD5167212D2DC69665F5A8875AB87F23D5CE7849132F56371A19096");
        byte[] digest = SHA3Utils.digest(INPUT, SHA3Utils.Size.S224);
        assertNotNull(digest);
        assertArrayEquals(EXPECTED_HASH, digest);

        // len: 24
        final byte[] INPUT2 = DatatypeConverter.parseHexBinary("1F877C");
        final byte[] EXPECTED_HASH2 = DatatypeConverter.parseHexBinary("14889DF49C076A9AF2F4BCB16339BCC45A24EBF9CE4DCDCE7EC17217");
        byte[] digest2 = SHA3Utils.digest(INPUT2, SHA3Utils.Size.S224);
        assertNotNull(digest2);
        assertArrayEquals(EXPECTED_HASH2, digest2);

        // len: 128
        final byte[] INPUT3 = DatatypeConverter.parseHexBinary("52A608AB21CCDD8A4457A57EDE782176");
        final byte[] EXPECTED_HASH3 = DatatypeConverter.parseHexBinary("B1571BED52E54EEF377D99DF7BE4BC6682C43387F2BF9ACC92DF608F");
        byte[] digest3 = SHA3Utils.digest(INPUT3, SHA3Utils.Size.S224);
        assertNotNull(digest3);
        assertArrayEquals(EXPECTED_HASH3, digest3);
    }
}