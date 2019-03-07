package it.nicolagiacchetta.crypto;

public interface HomomorphicHash {

    byte[] add(byte[] input);

    byte[] addAll(byte[]... inputs);

    byte[] remove(byte[] input);

    byte[] removeAll(byte[]... inputs);

    byte[] update(byte[] oldValue, byte[] newValue);

    void reset();

    void setChecksum(byte[] checksum);

    byte[] getChecksum();

    boolean equals(byte[] otherChecksum);

}
