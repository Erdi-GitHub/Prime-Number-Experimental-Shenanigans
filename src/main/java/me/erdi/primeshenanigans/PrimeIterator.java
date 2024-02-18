package me.erdi.primeshenanigans;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface PrimeIterator<T extends Number> extends Iterator<T> {
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(this, Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }
}
