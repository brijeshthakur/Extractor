package example.extractor;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;

import example.extractor.model.Emoticons;

public class EmoticonsExtractor extends Extractor {


    public EmoticonsExtractor() {
        super(RegEx.EMOTICONS);
    }

    @Override
    public Emoticons extract(@NonNull final String statement) {
        Matcher matcher = pattern.matcher(statement);
        Emoticons emoticons = new Emoticons();
        while (matcher.find()) {
            String emoticon = matcher.group();
            // Emoticons length shouldn't be greater than 15, including ( & ) should be a length of
            // 17.
            if (emoticon.length() < 18) {
                emoticons.add(emoticon.substring(1, emoticon.length() - 1));
            }
        }
        return emoticons;
    }
}
