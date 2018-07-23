package goosechain.crypto.DSA;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class DSAParametersTest {

    private static final BigInteger myP = DSAParameters.getP();
    private static final BigInteger myQ = DSAParameters.getQ();
    private static final BigInteger myG = DSAParameters.getG();

    @Test
    void getP()
    {
        assertNotNull(myP);

        // validation of parameter P
        // is probably prime with (1- 1/256) probability
        assertTrue(myP.isProbablePrime(8));
        // length is a multiple of 64
        assertEquals(myP.bitLength()%64, 0);
        // length is between 512 and 1024
        assertTrue(myP.bitLength()>=512);
        assertTrue(myP.bitLength()<=1024);

        // assertEquals(myP.toString(), "168199388701209853920129085113302407023173962717160229197318545484823101018386724351964316301278642143567435810448472465887143222934545154943005714265124445244247988777471773193847131514083030740407543233616696550197643519458134465700691569680905568000063025830089599260400096259430726498683087138415465107499");

    }

    @Test
    void getQ()
    {
        assertNotNull(myQ);

        // validation of parameter Q
        // is probably prime with (1- 1/256) probability
        assertTrue(myQ.isProbablePrime(8));
        // is a factor of P-1
        assertTrue(myP.subtract(BigInteger.ONE).remainder(myQ).equals(BigInteger.ZERO));
        // length is 160 bits
        assertEquals(myQ.bitLength(), 160);
        // assertEquals(myQ.toString(), "959452661475451209325433595634941112150003865821");
    }

    @Test
    void getG()
    {
        assertNotNull(myG);

        // validation of parameter g according to Appendix A.2 of FIPS 186-4: Partial Validation
        final BigInteger TWO = BigInteger.valueOf(2);
        // 2<= G <= P-1
        assertTrue(myG.compareTo(TWO)>=0);
        assertTrue(myG.compareTo(myP.subtract(BigInteger.ONE))<=0);
        // G^Q = 1 mod P
        assertTrue(myG.modPow(myQ, myP).equals(BigInteger.ONE));

        // assertEquals(myG.bitLength(), 1024);
        // assertEquals(myG.toString(),"94389192776327398589845326980349814526433869093412782345430946059206568804005181600855825906142967271872548375877738949875812540433223444968461350789461385043775029963900638123183435133537262152973355498432995364505138912569755859623649866375135353179362670798771770711847430626954864269888988371113567502852");
    }
}