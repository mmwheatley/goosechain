package goosecoin.crypto.DSA;

import java.math.BigInteger;

public class DSAKeyPair {
    private BigInteger privateKey;
    private BigInteger publicKey;

    public DSAKeyPair(BigInteger privateKey, BigInteger publicKey)
    {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public BigInteger getPrivateKey()
    {
        return this.privateKey;
    }

    public BigInteger getPublicKey()
    {
        return this.publicKey;
    }
}
