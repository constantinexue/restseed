package constantinexue.restseed.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PagedList<T> implements Iterable<T> {
    
    private long total;
    private ArrayList<T> list;
    
    public PagedList() {
        list = new ArrayList<T>();
    }
    
    public PagedList(long total) {
        this();
        this.total = total;
    }
    
    public PagedList(Collection<T> items, long total) {
        this.list = new ArrayList<T>(items);
        this.total = total;
    }
    
    public List<T> getInnerList() {
        return list;
    }
    
    public void add(T item) {
        list.add(item);
    }
    
    public void setTotal(long value) {
        this.total = value;
    }
    
    public long getTotal() {
        return total;
    }
    
    public int getCount() {
        return list.size();
    }
    
    public T get(int index) {
        return list.get(index);
    }
    
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
