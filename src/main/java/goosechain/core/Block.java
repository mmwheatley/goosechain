package goosechain.core;

import goosechain.crypto.DSA.DSASignature;
import goosechain.utils.SHA3Utils;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;

public class Block {

    /**
     * dsaSignature: User's Signature
     * transaction: digest of the proofOfWork
     */
    private DSASignature dsaSignature;
    private byte[] transaction;

    @Override
    public String toString() {
        return "Signature: " + dsaSignature.toString() +
                System.lineSeparator() +
                "Transaction: " + DatatypeConverter.printHexBinary(transaction);
    }

    public Block(DSASignature dsaSignature, byte[] transaction)
    {
        this.dsaSignature = dsaSignature;
        this.transaction = transaction;
    }

    public DSASignature getDsaSignature()
    {
        return dsaSignature;
    }

    public byte[] getTransaction()
    {
        return transaction;
    }
}
