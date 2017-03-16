package extractor;

import android.util.Patterns;

public enum RegEx {
    MENTION("@\\w+"),
    LINK(Patterns.WEB_URL.pattern()),
    EMOTICONS("\\(\\w+\\)");

    String value;

    RegEx(String regex) {
      value = regex;
    }

}
