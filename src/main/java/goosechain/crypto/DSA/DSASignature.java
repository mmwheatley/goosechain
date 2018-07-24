package goosechain.crypto.DSA;

import java.math.BigInteger;

public class DSASignature {

    private BigInteger r;
    private BigInteger s;

    private DSASignature() {}

    protected DSASignature(BigInteger r, BigInteger s)
    {
        this.r = r;
        this.s = s;
    }

    @Override
    public String toString() {
        return "\"Signature\": {" +
                System.lineSeparator() +
                "\t"+"\"r\": " + r.toString() +
                System.lineSeparator() +
                "\t"+"\"s\": " + s.toString() +
                System.lineSeparator() +
                "}";

    }

    protected BigInteger getR()
    {
        return this.r;
    }

    protected BigInteger getS()
    {
        return this.s;
    }
}
