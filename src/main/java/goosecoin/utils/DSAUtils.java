package goosecoin.utils;

import java.security.*;

public class DSAUtils {
    private static final String SIGNING_ALGORITHM = "SHA1withDSA";
    private static final String DSA = "DSA";

    public static KeyPair generateDSAKeyPair() throws Exception
    {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(DSA);
        keyPairGenerator.initialize(1024, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] createDigitalSignature(byte[] input, PrivateKey privateKey) throws Exception
    {
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(input);

        return signature.sign();
    }

    public static boolean verifyDigitalSignature(byte[] input, byte[] signatureToVerify, PublicKey publickey) throws Exception
    {
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initVerify(publickey);
        signature.update(input);
        return signature.verify(signatureToVerify);
    }
}
