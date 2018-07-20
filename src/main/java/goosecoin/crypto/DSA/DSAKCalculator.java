package goosecoin.crypto.DSA;

import java.math.BigInteger;
import java.security.SecureRandom;

public class DSAKCalculator {

    private BigInteger q;
    private SecureRandom random;

    public void init(BigInteger n, SecureRandom random)
    {
        this.q = n;
        this.random = random;
    }

    public BigInteger nextK()
    {
        int qBitLength = q.bitLength();

        BigInteger k;
        do
        {
            k = new BigInteger(qBitLength, random);
        }
        while (k.equals(BigInteger.ZERO) || k.compareTo(q) >= 0);

        return k;
    }
}
