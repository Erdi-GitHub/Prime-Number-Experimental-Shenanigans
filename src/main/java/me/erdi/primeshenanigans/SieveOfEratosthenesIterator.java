package me.erdi.primeshenanigans;

import java.util.BitSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SieveOfEratosthenesIterator implements PrimeIterator<Integer> {
    private final BitSet primeTape;
    private final int size;
    private final int maxCompareSize;

    private int index = 1;

    public static final int MAX_SIZE = Integer.MAX_VALUE;

    public SieveOfEratosthenesIterator(int size) {
        if(size < 0)
            throw new UnsupportedOperationException("can't have a negative size, silly :3");

        if(size < 2)
            throw new UnsupportedOperationException("bit too small.. :( size should be >= 2");

        this.primeTape = new BitSet(size);
        this.size = size;
        this.maxCompareSize = (int)Math.sqrt(size);

        this.primeTape.set(1, size, true);
    }

    public int getMaxCompareSize() {
        return maxCompareSize;
    }

    public Stream<Boolean> tapeStream() {
        return IntStream.range(0, size).mapToObj(primeTape::get);
    }

    @Override
    public boolean hasNext() {
        boolean reachedEnd;

        while((reachedEnd = index + 1 != size) && !primeTape.get(index))
            index++;

        return reachedEnd;
    }

    @Override
    public Integer next() {
        int n = index + 1;

        if(index <= maxCompareSize)
            for(int i = index + n; i > 0 && i < size; i += n)
                primeTape.set(i, false);

        if(n != size)
            index++;

        return n;
    }
}
