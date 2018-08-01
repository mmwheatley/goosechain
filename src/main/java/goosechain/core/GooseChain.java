package goosechain.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import goosechain.utils.DSAUtils;
import goosechain.utils.SHA3Utils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GooseChain {

    private static final Type USER_LIST_TYPE = new TypeToken<List<User>>() {}.getType();
    private static final Type BLOCK_CHAIN_TYPE = new TypeToken<List<Block>>() {}.getType();

    private static final String HOME = System.getProperty("user.home");
    private static final Path USER_LIST_PATH = Paths.get(HOME,"goosechain", "metadata", "user_list.json");
    private static final Path BLOCK_CHAIN_PATH = Paths.get(HOME,"goosechain", "metadata", "block_chain.json");

    public static void main(String args[]) throws IOException {

        List<User> userList;
        List<Block> blockChain;
        Gson gson;
        Writer fw;
        Instant start, end;

        /**
         * UserList routine
         *
         * Check to see if we have a user database, If so, import it.
         * Otherwise, create the JSON files and write to them.
         */
        gson = User.getGsonInstance();
        try{
            /*
            Try to Open File Streams to the User list and Blockchain if they exist
            If not, catch the exception and create them.
             */

            final BufferedReader USER_LIST_BR = new BufferedReader(new InputStreamReader(Files.newInputStream(USER_LIST_PATH)));
            userList = gson.fromJson(USER_LIST_BR, USER_LIST_TYPE);
            USER_LIST_BR.close();

            /*
            final BufferedReader BLOCKCHAIN_BR = new BufferedReader(new InputStreamReader(Files.newInputStream(BLOCK_CHAIN_PATH)));
            blockChain = gsonReader.fromJson(BLOCKCHAIN_BR, BLOCK_CHAIN_TYPE);
            BLOCKCHAIN_BR.close();
            */

        } catch (NoSuchFileException e) {

            // create the dirs
            Files.createDirectories(USER_LIST_PATH.getParent());
            Files.createFile(USER_LIST_PATH);

            userList = new ArrayList<>();
            userList.add(new User("User1"));
            userList.add(new User("User2"));
            userList.add(new User("User3"));

            fw = new FileWriter(USER_LIST_PATH.toString());
            fw.write(gson.toJson(userList));
            fw.close();
        }

        /**
         * BlockChain routine.
         * User 1 mines a block and signs it.
         * User 2 then verifies user 1's block and mines another block and signs it.
         */

        gson = Block.getGsonInstance();
        Files.deleteIfExists(BLOCK_CHAIN_PATH);
        Files.createDirectories(BLOCK_CHAIN_PATH.getParent());
        Files.createFile(BLOCK_CHAIN_PATH);
        blockChain = new ArrayList<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] message;

        // User 1 mining
        System.out.println("User 1 is Mining!");
        outputStream.write(userList.get(0).getPublicKey().toByteArray());
        outputStream.write(userList.get(1).getPublicKey().toByteArray());
        outputStream.write(1);

        message = SHA3Utils.digest(outputStream.toByteArray());

        start = Instant.now();
        blockChain.add(userList.get(0).mine(1, message));
        end = Instant.now();
        System.out.println("User 1 Finished mining in: "+ Duration.between(start, end).toMillis());
        System.out.println(blockChain.get(0));

        // User 2 verifies
        if(DSAUtils.verifyDigitalSignature(
                blockChain.get(0).getMessage(),
                blockChain.get(0).getDsaSignature(),
                userList.get(0).getPublicKey())) {
            System.out.println("User 2 Successfully Verified User 1's Signature.");
        } else {
            System.out.println("User 2 was not able to verify the Signature of User 1.");
        }

        // User 2 mining
        System.out.println("User 2 is Mining!");
        outputStream.reset();

        outputStream.write(userList.get(1).getPublicKey().toByteArray());
        outputStream.write(userList.get(2).getPublicKey().toByteArray());
        outputStream.write(1);

        message = SHA3Utils.digest(outputStream.toByteArray());

        start = Instant.now();
        blockChain.add(userList.get(1).mine(1, message));
        end = Instant.now();
        System.out.println("User 2 Finished mining in: "+ Duration.between(start, end).toMillis());
        System.out.println(blockChain.get(1));

        // Write out the blockchain to file
        System.out.println("Writing Blockchain to JSON...");
        fw = new FileWriter(BLOCK_CHAIN_PATH.toString());
        fw.write(gson.toJson(blockChain));
        fw.close();
        System.out.println("Done.");
    }
}
