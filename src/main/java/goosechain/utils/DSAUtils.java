package goosechain.utils;

import goosechain.crypto.DSA.DSAKeyPair;
import goosechain.crypto.DSA.DSAKeyPairGenerator;
import goosechain.crypto.DSA.DSASignature;
import goosechain.crypto.DSA.DSASigner;

import java.math.BigInteger;
import java.security.SecureRandom;

public class DSAUtils {
    /*
    private static final String SIGNING_ALGORITHM = "SHA1withDSA";
    private static final String DSA = "DSA";
    */

    public static DSAKeyPair generateDSAKeyPair() {
        SecureRandom secureRandom = new SecureRandom();
        DSAKeyPairGenerator dsaKeyPairGenerator = new DSAKeyPairGenerator(secureRandom);
        return dsaKeyPairGenerator.generateKeyPair();
    }

    public static DSASignature createDigitalSignature(byte[] input, BigInteger privateKey) {

        DSASigner dsaSigner = new DSASigner();
        dsaSigner.initSign(privateKey);
        return dsaSigner.generateSignature(input);
    }

    public static boolean verifyDigitalSignature(byte[] input, DSASignature signature, BigInteger publicKey) {
        DSASigner dsaSigner = new DSASigner();
        dsaSigner.initVerify(publicKey);
        return dsaSigner.verifySignature(input, signature);
    }
}
