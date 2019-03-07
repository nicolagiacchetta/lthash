package it.nicolagiacchetta.crypto;

import org.junit.Assert;
import org.junit.Test;

public class LtHashTest {

    @Test
    public void test() {
        LtHash ltHash = new LtHash();
        ltHash.addAll("apple".getBytes(), "orange".getBytes(), "banana".getBytes());
        byte[] checksum = ltHash.getChecksum();

        LtHash secondLtHash = new LtHash();
        secondLtHash.addAll("apple".getBytes(), "orange".getBytes(), "banana".getBytes());
        byte[] otherChecksum = secondLtHash.getChecksum();

        Assert.assertTrue(ltHash.equals(otherChecksum));
        Assert.assertTrue(secondLtHash.equals(checksum));
    }



}