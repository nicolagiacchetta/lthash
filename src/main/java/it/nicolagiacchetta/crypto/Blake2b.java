package it.nicolagiacchetta.crypto;

import java.nio.ByteBuffer;
import java.util.Objects;

public class Blake2b {

    private static final ove.crypto.digest.Blake2b.Digest BLAKE_2_B = ove.crypto.digest.Blake2b.Digest.newInstance();

    private static final int DEFAULT_SIZE = 2048;

    public static byte[] digest(byte[] input) {
        return digest(input, DEFAULT_SIZE);
    }

    public static byte[] digest(byte[] input, int size) {
        Objects.requireNonNull(input);

        if(size % Byte.SIZE != 0)
            throw new IllegalArgumentException("Illegal argument 'size' must be multiple of " + Byte.SIZE);

        if(size == 0)
            return new byte[0];


        byte[] hash = BLAKE_2_B.digest(input);
        return adapt(hash, size);
    }

    private static byte[] adapt(byte[] input, int size) {
        if(input.length == size)
            return input;
        byte[] output = new byte[size];
        ByteBuffer wrap = ByteBuffer.wrap(output);
        while(wrap.hasRemaining()) {
            wrap.putInt(0);
        }
        System.arraycopy(input, 0, output,0, Math.min(input.length, size));
        return output;
    }
}

