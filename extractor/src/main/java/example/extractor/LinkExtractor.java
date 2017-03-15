package example.extractor;

import android.support.annotation.NonNull;
import android.webkit.URLUtil;

import java.util.regex.Matcher;

import example.extractor.model.Link;
import example.extractor.model.Links;

public class LinkExtractor extends Extractor {

    public LinkExtractor() {
        super(RegEx.LINK);
    }

    @Override
    public Links extract(@NonNull final String statement) {
        Matcher matcher = pattern.matcher(statement);
        Links links = new Links();
        while (matcher.find()) {
            String url = matcher.group();
            if (URLUtil.isValidUrl(url)) {
                Link link = new Link();
                link.setUrl(matcher.group());
                links.add(link);
            }
        }
        return links;
    }
}
