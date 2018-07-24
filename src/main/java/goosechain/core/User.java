package goosechain.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import goosechain.crypto.DSA.DSAKeyPair;
import goosechain.crypto.DSA.DSASignature;
import goosechain.utils.DSAUtils;
import goosechain.utils.SHA3Utils;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class User {

    /**
     * enum that enumerates the various difficulty levels
     * in mining blocks. enum fields represent the number of leading
     * zeros that a message digest must have for the transaction to be accepted
     */
    protected enum Difficulty {
        baby(8),
        java(24),
        cpp(32);

        private int difficulty;

        int getDifficulty() {
            return this.difficulty;
        }

        Difficulty(int difficulty) {
            this.difficulty = difficulty;
        }
    }

    private static final Difficulty DEFAULT_DIFFCULTY = Difficulty.baby;

    // singleton instance of a gson builder for User class
    private static final Gson userGsonBuilder = new GsonBuilder().setPrettyPrinting().create();

    public String name;
    private DSAKeyPair dsaKeyPair;

    private User() {}

    public User(String name)
    {
        this.name = name;
        this.dsaKeyPair = DSAUtils.generateDSAKeyPair();
    }

    @Override
    public String toString() {
        return "Name: " + name +
                System.lineSeparator() +
                "Public Key: " + DatatypeConverter.printHexBinary(dsaKeyPair.getPublicKey().toByteArray());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            return dsaKeyPair.getPublicKey().equals(((User) obj).dsaKeyPair.getPublicKey());
        }
        else return false;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(SHA3Utils.digest(this.dsaKeyPair.getPublicKey().toByteArray()).toString());
    }

    public String getName()
    {
        return name;
    }

    public BigInteger getPublicKey()
    {
        return dsaKeyPair.getPublicKey();
    }

    public static Gson getGsonInstance()
    {
        return userGsonBuilder;
    }

    public DSASignature sign(byte[] message)
    {
        return DSAUtils.createDigitalSignature(message, dsaKeyPair.getPrivateKey());
    }

    /**
     * @brief mine a new block on the blockchain with default difficulty (24 leading zeroes)
     * and starting the nonce at 0
     *
     * @param amt The amount of goosecoin the Transaction uses. 1 for all Blocks in this Project
     * @param message The message digest comprosing pk_current||pk_next||amt
     * @return A new block that has been mined.
     */
    public Block mine(int amt, byte[] message) throws IOException
    {
        return this.mine(amt, message, DEFAULT_DIFFCULTY);
    }

    /**
     * @brief mine a new block on the blockchain with custom difficulty and starting the nonce 0
     *
     * @param amt The amount of goosecoin the Transaction uses. 1 for all Blocks in this Project
     * @param m The message digest comprosing pk_current||pk_next||amt
     * @param d The number of leading zeros to mine.
     * @return A new block that has been mined.
     */
    public Block mine(int amt, byte[] m, Difficulty d) throws IOException
    {
        return this.mine(amt, m, BigInteger.ZERO, d);
    }

    /**
     * @brief mine a new block on the blockchain with custom difficulty and custom nonce
     *
     * @param amt The amount of goosecoin the Transaction uses. 1 for all Blocks in this Project
     * @param m The message digest comprosing pk_current||pk_next||amt
     * @param nonce A nonce for finding the required leading zeros
     * @param d The number of leading zeros to mine.
     * @return A new block that has been mined.
     */
    public Block mine(int amt, byte[] m, BigInteger nonce, Difficulty d) throws IOException {
        // TODO: Add Timeout

        ByteArrayOutputStream amountStream = new ByteArrayOutputStream( );
        amountStream.write(amt);
        byte[] amount = amountStream.toByteArray();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        byte[] transaction;
        boolean difficultyHasNotBeenMet = false;

        do {
            outputStream.write(SHA3Utils.digest(amount));
            outputStream.write(m);
            outputStream.write(nonce.toByteArray());

            transaction = SHA3Utils.digest(outputStream.toByteArray());

            //System.out.println(DatatypeConverter.printHexBinary(transaction) + ", " + transaction.length);

            for(int i=0; i < d.getDifficulty()/8; i++) {
                if((transaction[i] & 0xFF) != (byte)0) {
                    difficultyHasNotBeenMet = true;
                    break;
                }
                difficultyHasNotBeenMet = false;
            }

            outputStream.reset();
            nonce = nonce.add(BigInteger.ONE);
        } while(difficultyHasNotBeenMet);

        return new Block(m, sign(m), transaction);

    }
}
