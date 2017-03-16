package extractor.model;

import java.util.ArrayList;
import java.util.List;

public class Links implements ExtractModel<Link> {

    List<Link> links = new ArrayList<>();

    @Override
    public void add(final Link link) {
        links.add(link);
    }

    @Override
    public List<Link> get() {
        return links;
    }

    @Override
    public Link getItemAt(final int index) {
        if (index > links.size() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return links.get(index);
    }
}
