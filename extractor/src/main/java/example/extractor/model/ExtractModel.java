package example.extractor.model;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public interface ExtractModel<T> {
    void add(T object);
    List<T> get();
    T getItemAt(int index);
}
