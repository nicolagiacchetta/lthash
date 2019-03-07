package it.nicolagiacchetta.crypto;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Blake2BDigestTest {

    private static Blake2bDigest digest;

    @BeforeClass
    public static void setUp() {
        digest = new Blake2bDigest();
    }

    @Test
    public void testSize_0() {
        testSize("hello", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSize_4() {
        testSize("hello", 4);
    }

    @Test
    public void testSize_8() {
        testSize("hello", 8);
    }

    @Test
    public void testSize_64() {
        testSize("hello", 64);
    }

    @Test
    public void testSize_128() {
        testSize("hello", 128);
    }

    @Test
    public void testSize_2048() {
        testSize("hello", 2048);
    }

    @Test(expected = NullPointerException.class)
    public void testInputNull() {
        digest.hash(null);
    }

    private void testSize(String input, int size) {
        try {
            byte[] output = digest.hash(input.getBytes(), size);
            assertEquals("Test Size failed for size=" + size + " got " + output.length, size, output.length);
        } catch (Throwable t) {
            System.out.println("\nTest Size failed for size=" + size + ": ");
            t.printStackTrace();
            throw t;
        }

    }
}