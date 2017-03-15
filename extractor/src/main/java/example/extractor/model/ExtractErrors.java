package example.extractor.model;

import java.util.ArrayList;
import java.util.List;

public class ExtractErrors implements ExtractModel<Errors.ExtractError> {

    List<Errors.ExtractError> extractErrors = new ArrayList<>();

    @Override
    public void add(final Errors.ExtractError error) {
        extractErrors.add(error);
    }

    @Override
    public List<Errors.ExtractError> get() {
        return extractErrors;
    }

    @Override
    public Errors.ExtractError getItemAt(final int index) {
        return null;
    }
}
