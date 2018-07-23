package goosechain.utils;

import goosechain.crypto.DSA.DSAKeyPair;
import goosechain.crypto.DSA.DSASignature;
import org.junit.jupiter.api.Test;
import javax.xml.bind.DatatypeConverter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DSAUtilsTest {

    @Test
    void generateDSAKeyPair()
    {
        DSAKeyPair keyPair = DSAUtils.generateDSAKeyPair();
        assertNotNull(keyPair);
        System.out.println("Private Key: " + DatatypeConverter.printHexBinary(keyPair.getPrivateKey().toByteArray()));
        System.out.println("Number of bits in the Private Key:" + keyPair.getPrivateKey().bitLength());
        System.out.println("Public Key: " + DatatypeConverter.printHexBinary(keyPair.getPublicKey().toByteArray()));
        System.out.println("Number of bits in the Public Key:" + keyPair.getPublicKey().bitLength());

    }

    @Test
    void digitalSignatureRoutine() throws Exception
    {
        URL uri = this.getClass().getClassLoader().getResource("aesops_fables.txt");
        Path path = Paths.get(uri.toURI());
        byte[] input = Files.readAllBytes(path);

        DSAKeyPair keyPair = DSAUtils.generateDSAKeyPair();
        DSASignature signature = DSAUtils.createDigitalSignature(input, keyPair.getPrivateKey());
        System.out.println("Private Key: " + DatatypeConverter.printHexBinary(keyPair.getPrivateKey().toByteArray()));
        System.out.println("Number of bits in the Private Key:" + keyPair.getPrivateKey().bitLength());
        System.out.println("Public Key: " + DatatypeConverter.printHexBinary(keyPair.getPublicKey().toByteArray()));
        System.out.println("Number of bits in the Public Key:" + keyPair.getPublicKey().bitLength());
        //System.out.println("Signature: " + DatatypeConverter.printHexBinary(signature));
        assertTrue(DSAUtils.verifyDigitalSignature(input, signature, keyPair.getPublicKey()));

    }

}