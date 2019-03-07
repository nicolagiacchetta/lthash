package it.nicolagiacchetta.crypto;

import ove.crypto.digest.Blake2b;

import java.nio.ByteBuffer;
import java.util.Objects;


public class Blake2bDigest implements Digest {

    private final Blake2b.Digest digest;

    private static final int DEFAULT_SIZE = 2048;

    public Blake2bDigest() {
        this.digest = Blake2b.Digest.newInstance();
    }

    public byte[] hash(byte[] input) {
        return hash(input, DEFAULT_SIZE);
    }

    public byte[] hash(byte[] input, int size) {
        Objects.requireNonNull(input);

        if(size % Byte.SIZE != 0)
            throw new IllegalArgumentException("Illegal argument 'size' must be multiple of " + Byte.SIZE);

        if(size == 0)
            return new byte[0];

        byte[] hash = this.digest.digest(input);
        return adaptSize(hash, size);
    }

    private static byte[] adaptSize(byte[] input, int size) {
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

