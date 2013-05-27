package constantinexue.restseed.server.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PagedObject<T> implements ValueObject, Iterable<T> {
    
    private long total;
    private ArrayList<T> objects;
    
    public PagedObject() {
    }
    
    public PagedObject(long total) {
        objects = new ArrayList<T>();
        this.total = total;
    }
    
    public PagedObject(Collection<T> pagedItems) {
        objects = new ArrayList<T>(pagedItems);
        this.total = objects.size();
    }
    
    public PagedObject(Collection<T> pagedItems, long total) {
        objects = new ArrayList<T>(pagedItems);
        this.total = total;
    }
    
    public void append(T object) {
        objects.add(object);
    }
    
    public long getTotal() {
        return total;
    }
    
    public int getCount() {
        return objects.size();
    }
    
    public Iterable<T> getObjects() {
        return objects;
    }
    
    public T get(int index) {
        return objects.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return objects.iterator();
    }
}
