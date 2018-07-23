package goosechain.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GooseChain {

    static List<User> userList = new ArrayList<>();
    static List<Block> blockChain = new ArrayList<>();

    public static void main(String args[]) throws IOException {
        // TODO Add reading and writing JSON data to functionality

        userList.add(new User("User1"));
        userList.add(new User("User2"));
        userList.add(new User("User3"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );


        outputStream.write(userList.get(0).getPublicKey().toByteArray());
        outputStream.write(userList.get(1).getPublicKey().toByteArray());
        outputStream.write(1);

        byte[] message = outputStream.toByteArray();

        blockChain.add(userList.get(0).mine(1, message));

        System.out.println();
        System.out.println(blockChain.get(0));

    }
}
