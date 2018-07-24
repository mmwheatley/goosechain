package goosechain.utils;

import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class HexTest {

    @Test
    void toHexString() {

        String TEST_STR = "CAFEF00D";
        byte[] TEST_ARR = DatatypeConverter.parseHexBinary(TEST_STR);
        String CONVERTED_STR = Hex.toHexString(TEST_ARR);
        System.out.println(CONVERTED_STR);
        assertTrue(CONVERTED_STR.equals(TEST_STR));

        TEST_STR = "1CEB00DA";
        TEST_ARR = DatatypeConverter.parseHexBinary(TEST_STR);
        CONVERTED_STR = Hex.toHexString(TEST_ARR);
        System.out.println(CONVERTED_STR);
        assertTrue(CONVERTED_STR.equals(TEST_STR));

        /*
        BigInteger TEST_BI = new BigInteger("0000BABE");
        TEST_ARR = TEST_BI.toByteArray();
        CONVERTED_STR = Hex.toHexString(TEST_ARR);
        System.out.println(CONVERTED_STR);
        System.out.println(DatatypeConverter.printHexBinary(TEST_ARR));
        //assertTrue(CONVERTED_STR.equals(TEST_STR));
        */
    }
}