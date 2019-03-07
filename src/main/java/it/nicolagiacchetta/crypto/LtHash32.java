package it.nicolagiacchetta.crypto;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

public class LtHash32 implements LtHash {

    private static final int INT_SIZE_IN_BYTES = Integer.SIZE/Byte.SIZE;
    private static final int CHECKSUM_SIZE = 2048;

    private final Digest digest;

    private byte[] checksum;

    public LtHash32() {
        this(new Blake2bDigest());
    }

    public LtHash32(Digest digest) {
        this.digest = digest;
        reset();
    }

    @Override
    public void add(byte[]... inputs) {
        applyInputsToChecksum(Integer::sum, inputs);
    }

    @Override
    public void remove(byte[]... inputs) {
        applyInputsToChecksum((a,b) -> a-b, inputs);
    }

    @Override
    public void update(byte[] oldValue, byte[] newValue) {
        remove(oldValue);
        add(newValue);
    }

    @Override
    public void reset() {
        this.checksum = new byte[CHECKSUM_SIZE];
        ByteBuffer checksumWrap = ByteBuffer.wrap(this.checksum);
        while(checksumWrap.hasRemaining()) {
            checksumWrap.putInt(0);
        }
    }

    @Override
    public void setChecksum(byte[] checksum) throws IllegalArgumentException {
        if(checksum.length != CHECKSUM_SIZE)
            throw new IllegalArgumentException("Illegal 'checksum' provided: the checksum must be of " + CHECKSUM_SIZE + " bytes");
        deepCopy(checksum, this.checksum);
    }

    @Override
    public byte[] getChecksum() {
        return deepCopyChecksum();
    }

    @Override
    public boolean checksumEquals(byte[] otherChecksum) {
        return Arrays.equals(this.checksum, otherChecksum);
    }

    private void applyInputsToChecksum(BiFunction<Integer, Integer, Integer> function, byte[]... inputs) {
        if(inputs != null) {
            for (byte[] input : inputs) {
                applyInputToChecksum(function, input);
            }
        }
    }

    private void applyInputToChecksum(BiFunction<Integer, Integer, Integer> function, byte[] input) {
        Objects.requireNonNull(input);
        byte[] hash = this.digest.hash(input);
        this.checksum = applyHashToChecksum(function, hash);
    }

    private byte[] applyHashToChecksum(BiFunction<Integer, Integer, Integer> function, byte[] inputHash) {
        ByteBuffer checksumWrap = ByteBuffer.wrap(this.checksum);
        ByteBuffer newHashWrap = ByteBuffer.wrap(inputHash);
        for(int i=0; i<inputHash.length; i+=INT_SIZE_IN_BYTES) {
            int sum = function.apply(checksumWrap.getInt(), newHashWrap.getInt());
            checksumWrap.putInt(i, sum);
        }
        checksumWrap.rewind();
        return checksumWrap.array();
    }

    private byte[] deepCopyChecksum() {
        byte[] copy = new byte[CHECKSUM_SIZE];
        deepCopy(this.checksum, copy);
        return copy;
    }

    private void deepCopy(byte[] source, byte[] destination) {
        if(source.length != destination.length)
            throw new IllegalArgumentException("Bad input arrays in deep copy");
        System.arraycopy(source, 0, destination, 0, CHECKSUM_SIZE);
    }
}
