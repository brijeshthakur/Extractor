package extractor;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * As of now we have requirement of Title only, so exposing this method only. Going forward if we
 * need info like thumbnail Url, description etc., we can fetch these information by traversing the
 * Document.
 */
public final class LinkPreviewRequest {

    private static final String TAG = "LinkPreviewRequest";

    public static String getTitle(String url) throws IOException {

            Connection connection = Jsoup.connect(url);
            Document doc = connection.get();
            String title = doc.title();
            return title;
     }
}
