package utils;

import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.*;

class DSAUtilsTest {

    @org.junit.jupiter.api.Test
    void digitalSignatureRoutine() throws Exception
    {
        URL uri = this.getClass().getClassLoader().getResource("aesops_fables.txt");
        Path path = Paths.get(uri.toURI());
        byte[] input = Files.readAllBytes(path);

        KeyPair keyPair = DSAUtils.generateDSAKeyPair();
        byte[] signature = DSAUtils.createDigitalSignature(input, keyPair.getPrivate());
        System.out.println("Private Key: " + DatatypeConverter.printHexBinary(keyPair.getPrivate().getEncoded()));
        System.out.println("Public Key: " + DatatypeConverter.printHexBinary(keyPair.getPublic().getEncoded()));
        System.out.println("Signature: " + DatatypeConverter.printHexBinary(signature));
        assertTrue(DSAUtils.verifyDigitalSignature(input, signature, keyPair.getPublic()));

    }

    @org.junit.jupiter.api.Test
    void generateDSAKeyPair() throws Exception
    {
        KeyPair keyPair = DSAUtils.generateDSAKeyPair();
        assertNotNull(keyPair);
        System.out.println("Private Key: " + DatatypeConverter.printHexBinary(keyPair.getPrivate().getEncoded()));
        System.out.println("Public Key: " + DatatypeConverter.printHexBinary(keyPair.getPublic().getEncoded()));
    }
}