package it.nicolagiacchetta.crypto;

import org.junit.Assert;
import org.junit.Test;

public class LtHashTest {

    @Test
    public void test() {


        LtHash ltHash = new LtHash();
        ltHash.addAll("apple".getBytes(), "orange".getBytes(), "banana".getBytes());
        byte[] value = ltHash.getValue();


        LtHash secondLtHash = new LtHash();
        secondLtHash.addAll("apple".getBytes(), "orange".getBytes(), "banana".getBytes());
        byte[] secondValue = secondLtHash.getValue();


        Assert.assertArrayEquals(value, secondValue);


    }



}