package me.erdi.primeshenanigans.demo;

import me.erdi.primeshenanigans.SieveOfEratosthenesIterator;

public class PerformanceDemo {
    public static void main(String[] args) {
        int size = SieveOfEratosthenesIterator.MAX_SIZE;
        System.out.println("Computing primes up to " + size + "...");

        long startMem = usedMemory();
        long start = System.nanoTime();

        long primes = new SieveOfEratosthenesIterator(size).stream().count();

        long end = System.nanoTime();
        long endMem = usedMemory();

        System.out.println(primes + " primes (" + size + " numbers checked) took " + ((end - start) / 1000000f) + "ms to find");

        System.out.print("Memory usage: approx. ");
        System.out.println(((endMem - startMem) / 1024f / 1024f) + "MiB");
    }

    private static long usedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
