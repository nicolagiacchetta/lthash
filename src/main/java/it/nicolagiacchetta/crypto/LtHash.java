package it.nicolagiacchetta.crypto;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

public class LtHash implements HomomorphicHash {

    private static final int INT_SIZE_IN_BYTES = Integer.SIZE/Byte.SIZE;

    private final Digest digest;

    private byte[] checksum;

    public LtHash() {
        this(new Blake2bDigest());
    }

    public LtHash(Digest digest) {
        this.digest = digest;
        reset();
    }

    @Override
    public byte[] add(byte[] input) {
        return applyInputToChecksum(input, Integer::sum);
    }

    @Override
    public byte[] addAll(byte[]... inputs) {
        for (byte[] input : inputs) {
            add(input);
        }
        return this.checksum;
    }

    @Override
    public byte[] remove(byte[] input) {
        return applyInputToChecksum(input, (a, b) -> a-b);
    }

    @Override
    public byte[] removeAll(byte[]... inputs) {
        for (byte[] input : inputs) {
            remove(input);
        }
        return this.checksum;
    }

    @Override
    public byte[] update(byte[] oldValue, byte[] newValue) {
        remove(oldValue);
        return add(newValue);
    }

    @Override
    public void reset() {
        this.checksum = new byte[2048];
        ByteBuffer currentWrapper = ByteBuffer.wrap(this.checksum);
        while(currentWrapper.hasRemaining()) {
            currentWrapper.putInt(0);
        }
    }

    @Override
    public void setChecksum(byte[] checksum) {
        this.checksum = checksum;
    }

    @Override
    public byte[] getChecksum() {
        return checksum;
    }

    @Override
    public boolean equals(byte[] otherChecksum) {
        return Arrays.equals(this.checksum, otherChecksum);
    }

    private byte[] applyInputToChecksum(byte[] input, BiFunction<Integer, Integer, Integer> function) {
        Objects.requireNonNull(input);
        byte[] hash = this.digest.hash(input);
        this.checksum = applyHashToChecksum(hash, function);
        return this.checksum;
    }

    private byte[] applyHashToChecksum(byte[] inputHash, BiFunction<Integer, Integer, Integer> function) {
        ByteBuffer wrapCurrent = ByteBuffer.wrap(this.checksum);
        ByteBuffer wrapNewHash = ByteBuffer.wrap(inputHash);
        for(int i=0; i<inputHash.length; i+=INT_SIZE_IN_BYTES) {
            int sum = function.apply(wrapCurrent.getInt(), wrapNewHash.getInt());
            wrapCurrent.putInt(i, sum);
        }
        wrapCurrent.rewind();
        return wrapCurrent.array();
    }

}
