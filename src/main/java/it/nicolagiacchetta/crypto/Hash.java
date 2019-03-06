package it.nicolagiacchetta.crypto;

public interface Hash {

    byte[] add(byte[] input);

    byte[] addAll(byte[]... inputs);

    byte[] remove(byte[] input);

    byte[] removeAll(byte[]... inputs);

    byte[] update(byte[] oldValue, byte[] newValue);

    void reset();

    byte[] getValue();
}
