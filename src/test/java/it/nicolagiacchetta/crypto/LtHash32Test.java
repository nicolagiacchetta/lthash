package it.nicolagiacchetta.crypto;

import org.junit.Assert;
import org.junit.Test;

public class LtHash32Test {

    @Test
    public void testUpdateFalseToTrue() {
        LtHash32 ltHash = new LtHash32();
        ltHash.add("apple".getBytes(), "orange".getBytes(), "banana".getBytes());
        LtHash32 secondLtHash = new LtHash32();
        secondLtHash.add("apple".getBytes(), "peach".getBytes(), "banana".getBytes());
        byte[] otherChecksum = secondLtHash.getChecksum();
        Assert.assertFalse(ltHash.checksumEquals(otherChecksum));
        secondLtHash.update("peach".getBytes(), "orange".getBytes());
        otherChecksum = secondLtHash.getChecksum();
        Assert.assertTrue(ltHash.checksumEquals(otherChecksum));
    }

    @Test
    public void testAddNullInputs() {
        LtHash32 ltHash = new LtHash32();
        byte[] checksum = ltHash.getChecksum();
        ltHash.add(null);
        Assert.assertTrue(ltHash.checksumEquals(checksum));
    }

    @Test
    public void testGeneralCorrectness() {
        LtHash32 ltHash = new LtHash32();

        // Create an initial checksum of the values in input
        ltHash.add("apple".getBytes(), "orange".getBytes());
        byte[] checksum = ltHash.getChecksum();

                // Remove the hash of "apple" from the checksum and check
        // if the 2 checksums are equals
        ltHash.remove("apple".getBytes());
        boolean isEqual = ltHash.checksumEquals(checksum);
        Assert.assertFalse(isEqual);

        // Update the hash of "orange" with the new value "apple"
        // and check if the 2 checksums are equals
        ltHash.update("orange".getBytes(), "apple".getBytes());
        isEqual = ltHash.checksumEquals(checksum);
        Assert.assertFalse(isEqual);

        // Adding again the missing "orange" and check if the
        // checksum has gotten back to the initial status
        ltHash.add("orange".getBytes());
        isEqual = ltHash.checksumEquals(checksum);
        Assert.assertTrue(isEqual);
    }
}