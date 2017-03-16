package extractor.model;

import java.util.List;

public interface ExtractModel<T> {
    void add(T object);
    List<T> get();
    T getItemAt(int index);
}
