package goosecoin.crypto.DSA;

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

    protected BigInteger getR()
    {
        return this.r;
    }

    protected BigInteger getS()
    {
        return this.s;
    }
}
