package collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {

private final Set<T> set;
private Semaphore semaphore;

    public BoundedHashSet(int bound) {
        if(bound<1)
             throw new IllegalArgumentException();
        set = Collections.synchronizedSet(new HashSet<T>());
        semaphore = new Semaphore(bound);
    }

    public boolean add (T e) throws InterruptedException{
        semaphore.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(e);
            return wasAdded;
        }finally {
            if(!wasAdded){
                semaphore.release();
            }

        }
    }
    public boolean remove(T e){
        boolean wasRemoved = set.remove(e);
        if(wasRemoved)
            semaphore.release();
        return wasRemoved;
    }
}
