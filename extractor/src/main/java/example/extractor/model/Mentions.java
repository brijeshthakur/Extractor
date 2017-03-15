package example.extractor.model;

import java.util.ArrayList;
import java.util.List;

public class Mentions implements ExtractModel<String> {

    private List<String> mentions = new ArrayList<>();

    @Override
    public void add(final String mention) {
        mentions.add(mention);
    }

    @Override
    public List<String> get() {
        return mentions;
    }

    @Override
    public String getItemAt(final int index) {
        if (index > mentions.size() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return mentions.get(index);
    }
}
