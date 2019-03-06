package it.nicolagiacchetta.crypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Blake2bTest {

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
        Blake2b.digest(null);
    }

    private void testSize(String input, int size) {
        try {
            byte[] output = Blake2b.digest(input.getBytes(), size);
            assertEquals("Test Size failed for size=" + size + " got " + output.length, size, output.length);
        } catch (Throwable t) {
            System.out.println("\nTest Size failed for size=" + size + ": ");
            t.printStackTrace();
            throw t;
        }

    }
}