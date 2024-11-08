package interfaces;

import java.util.List;

public interface Service<T> {
    boolean add();
    boolean edit(T entity);
    boolean remove(T entity);
    boolean print();
    List<T> list();
}
