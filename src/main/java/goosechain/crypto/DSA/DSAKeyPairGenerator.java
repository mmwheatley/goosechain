package goosechain.crypto.DSA;

import java.math.BigInteger;
import java.security.SecureRandom;


/**
 * A DSA key pair generator.
 *
 * This generates DSA keys in line with the method described
 * in <i></i>FIPS 186-4 B.1 FFC Key Pair Generation</i>.
 */
public class DSAKeyPairGenerator {

    private SecureRandom random;

    private DSAKeyPairGenerator() { }

    public DSAKeyPairGenerator(SecureRandom random) {
        this.random = random;
    }

    public DSAKeyPair generateKeyPair() {
        BigInteger x = generatePrivateKey(DSAParameters.getQ(), random);
        BigInteger y = calculatePublicKey(DSAParameters.getP(), DSAParameters.getG(), x);

        return new DSAKeyPair(x, y);
    }

    private static BigInteger generatePrivateKey(BigInteger q, SecureRandom random) {
        return new BigInteger(q.bitLength() + 64, random).mod(q.subtract(BigInteger.ONE)).add(BigInteger.ONE);
    }

    private static BigInteger calculatePublicKey(BigInteger p, BigInteger g, BigInteger x) {
        return g.modPow(x, p);
    }
}
