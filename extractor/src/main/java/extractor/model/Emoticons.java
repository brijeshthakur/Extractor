package extractor.model;

import java.util.ArrayList;
import java.util.List;

public class Emoticons implements ExtractModel<String> {
    private List<String> emoticons = new ArrayList<>();

    @Override
    public void add(String emoticon) {
        emoticons.add(emoticon);
    }

    @Override
    public List<String> get() {
        return emoticons;
    }

    @Override
    public String getItemAt(final int index) {
        if (index > emoticons.size() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return emoticons.get(index);
    }
}
