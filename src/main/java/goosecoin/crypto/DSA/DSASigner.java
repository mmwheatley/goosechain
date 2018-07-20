package goosecoin.crypto.DSA;

import javax.management.openmbean.InvalidKeyException;
import java.math.BigInteger;
import java.security.SecureRandom;


/**
 * The Digital Signature Algorithm - as described in "Handbook of Applied
 * Cryptography", pages 452 - 453.
 */
public class DSASigner {

    private final DSAKCalculator kCalculator;

    private BigInteger      privateKey;
    private BigInteger      publicKey;

    /**
     * Default configuration, random K values.
     */
    public DSASigner()
    {
        this.kCalculator = new DSAKCalculator();
    }

    /**
     * initialize for signing
     *
     * @param privateKey The private key to use for Signing
     */
    public void initSign(BigInteger privateKey)
    {
        // TODO: Throws invalidKeyException
        this.privateKey = privateKey;
    }

    /**
     * initialize for verifying signature
     *
     * @param publicKey The private key to use for Signing
     */
    public void initVerify(BigInteger publicKey)
    {
        // TODO: Throws invalidKeyException
        this.publicKey = publicKey;
    }

    /**
     * generate a signature for the given message using the key we were
     * initialised with. For conventional DSA the message should be a SHA-1
     * hash of the message of interest.
     *
     * @param message the message that will be verified later.
     */
    public DSASignature generateSignature(byte[] message) throws InvalidKeyException
    {
        if(privateKey == null) {
            throw new InvalidKeyException("The Private Key is not valid");
        }
        final BigInteger      q = DSAParameters.getQ();
        final BigInteger      m = calculateE(q, message);
        final BigInteger      x = privateKey;
        SecureRandom random = new SecureRandom();

        kCalculator.init(q, random);

        BigInteger  k = kCalculator.nextK();

        // the randomizer is to conceal timing information related to k and x.
        BigInteger  r = DSAParameters.getG().modPow(k.add(getRandomizer(q, random)), DSAParameters.getP()).mod(q);

        k = k.modInverse(q).multiply(m.add(x.multiply(r)));

        BigInteger  s = k.mod(q);

        return new DSASignature( r, s );
    }

    /**
     * return true if the value r and s represent a DSA signature for
     * the passed in message. For our purposes, the message should be a
     * SHA3-224 hash of the real message to be verified.
     */
    public boolean verifySignature(byte[] message, DSASignature signature) throws InvalidKeyException
    {
        if(publicKey == null) {
            throw new InvalidKeyException("The Public Key is not valid");
        }
        final BigInteger      q = DSAParameters.getQ();
        final BigInteger      m = calculateE(q, message);
        final BigInteger      r = signature.getR();
        final BigInteger      s = signature.getS();

        if (BigInteger.ZERO.compareTo(r) >= 0 || q.compareTo(r) <= 0)
        {
            return false;
        }

        if (BigInteger.ZERO.compareTo(s) >= 0 || q.compareTo(s) <= 0)
        {
            return false;
        }

        BigInteger  w = s.modInverse(q);

        BigInteger  u1 = m.multiply(w).mod(q);
        BigInteger  u2 = r.multiply(w).mod(q);

        BigInteger p = DSAParameters.getP();
        u1 = DSAParameters.getG().modPow(u1, p);
        u2 = publicKey.modPow(u2, p);

        BigInteger  v = u1.multiply(u2).mod(p).mod(q);

        return v.equals(r);
    }

    private BigInteger calculateE(BigInteger n, byte[] message)
    {
        if (n.bitLength() >= message.length * 8)
        {
            return new BigInteger(1, message);
        }
        else
        {
            byte[] trunc = new byte[n.bitLength() / 8];

            System.arraycopy(message, 0, trunc, 0, trunc.length);

            return new BigInteger(1, trunc);
        }
    }

    private BigInteger getRandomizer(BigInteger q, SecureRandom provided)
    {
        // Calculate a random multiple of q to add to k. Note that g^q = 1 (mod p), so adding multiple of q to k does not change r.
        int randomBits = 7;

        return new BigInteger(randomBits, provided).add(BigInteger.valueOf(128)).multiply(q);
    }
}
