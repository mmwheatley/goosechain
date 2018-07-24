package goosechain.core;

import com.google.gson.*;
import goosechain.crypto.DSA.DSASignature;
import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Type;

public class Block {

    private static class ByteArrayToHexTypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return DatatypeConverter.parseHexBinary(json.getAsString());
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(DatatypeConverter.printHexBinary(src));
        }
    }

    private static final Gson blockGson = new GsonBuilder().registerTypeHierarchyAdapter(byte[].class,
            new ByteArrayToHexTypeAdapter()).setPrettyPrinting().create();
    /**
     * message: message message to be signed
     * dsaSignature: User's Signature on the message
     * transaction: digest of the proofOfWork
     *
     */
    private byte[] message;
    private DSASignature dsaSignature;
    private byte[] transaction;

    @Override
    public String toString() {
        return  dsaSignature.toString() +
                System.lineSeparator() +
                "\"Transaction\": " + DatatypeConverter.printHexBinary(transaction);
    }

    public Block(byte[] message, DSASignature dsaSignature, byte[] transaction) {
        this.message = message;
        this.dsaSignature = dsaSignature;
        this.transaction = transaction;
    }

    public static Gson getGsonInstance() {
        return blockGson;
    }

    public byte[] getMessage() {
        return message;
    }

    public DSASignature getDsaSignature() {
        return dsaSignature;
    }

    public byte[] getTransaction() {
        return transaction;
    }
}
