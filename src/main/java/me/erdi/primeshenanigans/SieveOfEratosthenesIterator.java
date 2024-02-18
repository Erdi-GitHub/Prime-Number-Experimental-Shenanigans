package me.erdi.primeshenanigans;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SieveOfEratosthenesIterator implements PrimeIterator<Integer> {
    private final boolean[] primeTape;
    private final int size;
    private final int maxCompareSize;

    private int index = 1;

    public static final int MAX_SIZE = Integer.MAX_VALUE - 8;

    public SieveOfEratosthenesIterator(int size) {
        if(size < 0)
            throw new UnsupportedOperationException("can't have a negative size, silly :3");

        if(size < 2)
            throw new UnsupportedOperationException("bit too small.. :( size should be >= 2");

        if(size > MAX_SIZE)
            throw new UnsupportedOperationException("that's too big! >:( size should be smaller than or equal to 2^31 - 9 (2147483639)");

        this.primeTape = new boolean[size];
        this.size = size;
        this.maxCompareSize = (int)Math.sqrt(size);

        Arrays.fill(this.primeTape, true);
        primeTape[0] = false;
    }

    public int getMaxCompareSize() {
        return maxCompareSize;
    }

    public Stream<Boolean> tapeStream() {
        return IntStream.range(0, primeTape.length).mapToObj(i -> primeTape[i]);
    }

    @Override
    public boolean hasNext() {
        boolean reachedEnd;

        while((reachedEnd = index + 1 != size) && !primeTape[index])
            index++;

        return reachedEnd;
    }

    @Override
    public Integer next() {
        int n = index + 1;

        if(index <= maxCompareSize)
            for(int i = index + n; i > 0 && i < size; i += n)
                primeTape[i] = false;

        if(n != size)
            index++;

        return n;
    }
}
