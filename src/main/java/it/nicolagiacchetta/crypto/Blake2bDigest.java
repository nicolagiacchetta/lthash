package it.nicolagiacchetta.crypto;

import ove.crypto.digest.Blake2b;

import java.nio.ByteBuffer;
import java.util.Objects;


public class Blake2bDigest implements Digest {

    private final Blake2b.Digest digest;

    private static final int DEFAULT_OUTPUT_SIZE_IN_BYTES = 2048;

    public Blake2bDigest() {
        this(Blake2b.Spec.max_digest_bytes);
    }

    public Blake2bDigest(int digestLenght) {
        this.digest = Blake2b.Digest.newInstance(digestLenght);
    }

    public byte[] hash(byte[] input) {
        return hash(input, DEFAULT_OUTPUT_SIZE_IN_BYTES);
    }

    public byte[] hash(byte[] input, int outputLenght) {
        Objects.requireNonNull(input);

        if(outputLenght % Byte.SIZE != 0)
            throw new IllegalArgumentException("Illegal argument 'outputLenght' must be multiple of " + Byte.SIZE);

        if(outputLenght == 0)
            return new byte[0];

        byte[] hash = this.digest.digest(input);
        return adaptSize(hash, outputLenght);
    }

    private static byte[] adaptSize(byte[] input, int outputLenght) {
        if(input.length == outputLenght)
            return input;
        byte[] output = new byte[outputLenght];
        ByteBuffer wrap = ByteBuffer.wrap(output);
        while(wrap.hasRemaining()) {
            wrap.putInt(0);
        }
        System.arraycopy(input, 0, output,0, Math.min(input.length, outputLenght));
        return output;
    }
}

