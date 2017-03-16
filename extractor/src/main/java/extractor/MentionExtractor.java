package extractor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.regex.Matcher;

import extractor.exception.ExtractException;
import extractor.model.Mentions;

public class MentionExtractor extends Extractor {

    public MentionExtractor() {
        super(RegEx.MENTION);
    }

    @Override
    public Mentions extract(@NonNull final String statement) throws ExtractException {
        if (TextUtils.isEmpty(statement)) {
            throw new ExtractException(EMPTY_STATEMENT_ERROR);
        }
        Matcher matcher = pattern.matcher(statement);
        Mentions mentions = new Mentions();
        while (matcher.find()) {
            String mention = matcher.group();
            mentions.add(mention.substring(1));
        }
        return mentions;
    }
}
