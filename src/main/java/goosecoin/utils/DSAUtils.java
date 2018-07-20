package goosecoin.utils;

import goosecoin.crypto.DSA.DSAKeyPair;
import goosecoin.crypto.DSA.DSAKeyPairGenerator;
import goosecoin.crypto.DSA.DSASignature;
import goosecoin.crypto.DSA.DSASigner;

import java.math.BigInteger;
import java.security.*;

public class DSAUtils {
    /*
    private static final String SIGNING_ALGORITHM = "SHA1withDSA";
    private static final String DSA = "DSA";
    */

    public static DSAKeyPair generateDSAKeyPair()
    {
        SecureRandom secureRandom = new SecureRandom();
        DSAKeyPairGenerator dsaKeyPairGenerator = new DSAKeyPairGenerator(secureRandom);
        return dsaKeyPairGenerator.generateKeyPair();
    }

    public static DSASignature createDigitalSignature(byte[] input, BigInteger privateKey)
    {

        DSASigner dsaSigner = new DSASigner();
        dsaSigner.initSign(privateKey);
        return dsaSigner.generateSignature(input);
    }

    public static boolean verifyDigitalSignature(byte[] input, DSASignature signature, BigInteger publicKey)
    {
        DSASigner dsaSigner = new DSASigner();
        dsaSigner.initVerify(publicKey);
        return dsaSigner.verifySignature(input, signature);
    }
}
