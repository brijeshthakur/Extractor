package extractor;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

import extractor.exception.ExtractException;

public abstract class Extractor<T> {

    public static String EMPTY_STATEMENT_ERROR = "statement can't be null or empty";
    protected Pattern pattern;

    public Extractor(RegEx regEx) {
        pattern = pattern.compile(regEx.value);
    }

    public abstract T extract(@NonNull String statement) throws ExtractException;
}
