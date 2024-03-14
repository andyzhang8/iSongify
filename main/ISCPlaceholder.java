import java.util.Iterator;

public class ISCPlaceholder<T extends Comparable<T>>
    implements IterableSortedCollection<T> {

    private T value;
    
    public boolean insert(T data)
	throws NullPointerException, IllegalArgumentException {
	value = data;
	return true;
    }

    public boolean contains(Comparable<T> data) {
	return true;
    }

    public boolean isEmpty() {
	return false;
    }
    
    public int size() {
	return 3;
    }

    public void clear() {
    }

    public void setIterationStartPoint(Comparable<T> startPoint) {	
    }

    public Iterator<T> iterator() {
	
	return java.util.Arrays.asList(value, value, value).iterator();
    }
}
