package it.nicolagiacchetta.crypto;


public interface LtHash {

    /**
     * Update the incremental checksum by adding the hash of the values provided
     * in input. The method will take care of hashing the inputs with the chosen
     * algorithm.
     *
     * The method is commutative: add(a, b) = add(b, a).
     *
     * @param inputs     the value to be added to the checksum. If inputs is null
     *                   the state of the checksum will not change
     */
    void add(byte[]... inputs);

    /**
     * Update the incremental checksum by subtracting the hash of the value provided
     * in input. The method will take care of hashing the input with the chosen
     * algorithm.
     *
     * The method is commutative: remove(a, b) = remove(b, a).
     *
     * @param inputs     the value to be subtracted from the checksum. If inputs is null
     *                   the state of the checksum will not change
     */
    void remove(byte[]... inputs);

    /**
     * This method has the exact same behaviour as the execution of the sequence of
     * a add(newValue) and remove(oldValue) operation.
     *
     * @param oldValue     the value to be subtracted from the checksum
     * @param newValue     the value to be added to the checksum
     */
    void update(byte[] oldValue, byte[] newValue);


    /**
     * Resets the checksum in this LtHash. This puts the hash into the same
     * state as if it was just after the instance was constructed.
     */
    void reset();

    /**
     * Set the checksum in this LtHash to the value provided in input.
     *
     * @param checksum      the new checksum
     * @throws IllegalArgumentException    if the checksum provided is not valid
     */
    void setChecksum(byte[] checksum) throws IllegalArgumentException;

    /**
     * Returns the current value of the checksum in this LtHash.
     *
     * @return the current value of the checksum
     */
    byte[] getChecksum();

    /**
     * Determines whether the checksum provided in input is equal to the
     * checksum in this LtHash.
     *
     * @param otherChecksum     the checksum that we want to compare
     * @return true if the value of the checksum in input is equal to the checksum
     *         in this LtHash and false otherwise.
     */
    boolean checksumEquals(byte[] otherChecksum);

}
