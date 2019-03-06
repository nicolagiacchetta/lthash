package it.nicolagiacchetta.crypto;

import java.nio.ByteBuffer;
import java.util.Objects;

public class LtHash implements Hash {

    private static final int INT_SIZE_IN_BYTES = Integer.SIZE/Byte.SIZE;

    private byte[] value;

    public LtHash() {
        reset();
    }

    @Override
    public byte[] add(byte[] input) {
        Objects.requireNonNull(input);
        byte[] hash = Blake2b.digest(input);
        this.value = add(this.value, hash);
        return this.value;
    }

    private byte[] add(byte[] current, byte[] newHash) {
        ByteBuffer wrapCurrent = ByteBuffer.wrap(current);
        ByteBuffer wrapNewHash = ByteBuffer.wrap(newHash);
        for(int i=0; i<newHash.length; i+=INT_SIZE_IN_BYTES) {
            int sum = wrapCurrent.getInt() + wrapNewHash.getInt();
            wrapCurrent.putInt(i, sum);
        }
        wrapCurrent.rewind();
        return wrapCurrent.array();
    }

    @Override
    public byte[] addAll(byte[]... inputs) {
        for (byte[] input : inputs) {
            add(input);
        }
        return this.value;
    }

    @Override
    public byte[] remove(byte[] input) {
        Objects.requireNonNull(input);
        byte[] hash = Blake2b.digest(input);
        this.value = remove(this.value, hash);
        return this.value;
    }

    private byte[] remove(byte[] current, byte[] hash) {
        ByteBuffer wrapCurrent = ByteBuffer.wrap(current);
        ByteBuffer wrapHash = ByteBuffer.wrap(hash);
        for (int i = 0; i<hash.length; i += INT_SIZE_IN_BYTES) {
            int sum = wrapCurrent.getInt() - wrapHash.getInt();
            wrapCurrent.putInt(i, sum);
        }
        wrapCurrent.rewind();
        return wrapCurrent.array();
    }

    @Override
    public byte[] removeAll(byte[]... inputs) {
        for (byte[] input : inputs) {
            remove(input);
        }
        return this.value;
    }

    @Override
    public byte[] update(byte[] oldValue, byte[] newValue) {
        remove(oldValue);
        return add(newValue);
    }

    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    public void reset() {
        this.value = new byte[2048];
        ByteBuffer currentWrapper = ByteBuffer.wrap(this.value);
        while(currentWrapper.hasRemaining()) {
            currentWrapper.putInt(0);
        }
    }

}
