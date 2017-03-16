package extractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import extractor.ExtractRequest;
import extractor.model.Errors;
import extractor.exception.ExtractException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ExtractRequestTest {

    ExtractRequest extractRequest = new ExtractRequest();

    @Test
    public void testMentionWithLink() throws ExtractException {
        String mentionWithLink = "Hey @Brijesh, Please check this https://www.example.com";
        extractRequest.extract(mentionWithLink,
                new ExtractRequest.ExtractResponseCallback<JSONObject>() {
                    @Override
                    public void onExtractComplete(final JSONObject response) throws JSONException {
                        assertFalse(response == null);
                        JSONArray mentionsJsonArray = response.getJSONArray("mentions");
                        assertEquals("Brijesh", mentionsJsonArray.getString(0));
                        JSONArray linksJsonArray = response.getJSONArray("links");
                        JSONObject link = linksJsonArray.getJSONObject(0);
                        assertEquals("https://www.example.com", link.getString("url"));
                    }

                    @Override
                    public void onExtractError(final Errors.ExtractError error) {
                        // Do nothing
                    }
                });
    }

    @Test
    public void testMentionWithEmoticons() throws ExtractException {
        String mentionWithEmoticons = "Hey @Brijesh (wink)";
        extractRequest.extract(mentionWithEmoticons,
                new ExtractRequest.ExtractResponseCallback<JSONObject>() {
                    @Override
                    public void onExtractComplete(final JSONObject response) throws JSONException {
                        assertFalse(response == null);
                        JSONArray mentionsJsonArray = response.getJSONArray("mentions");
                        assertEquals("Brijesh", mentionsJsonArray.getString(0));
                        JSONArray emoticonsJsonArray = response.getJSONArray("emoticons");
                        assertEquals("wink", emoticonsJsonArray.getString(1));
                    }

                    @Override
                    public void onExtractError(final Errors.ExtractError error) {

                    }
                });
    }

    @Test
    public void testLinkWithEmoticons() throws ExtractException {
        String linkWithEmoticons = "A good reference to start with http://www.example.com (smile)";
        extractRequest.extract(linkWithEmoticons,
                new ExtractRequest.ExtractResponseCallback<JSONObject>() {
            @Override
            public void onExtractComplete(final JSONObject response) throws JSONException {
                assertFalse(response == null);
                JSONArray linkJsonArray = response.getJSONArray("links");
                JSONObject linkJson = linkJsonArray.getJSONObject(0);
                assertEquals("http://www.example.com", linkJson.getString("url"));
                JSONArray emoticonsJsonArray = response.getJSONArray("emoticons");
                assertEquals("smile", emoticonsJsonArray.getString(0));
            }

            @Override
            public void onExtractError(final Errors.ExtractError error) {

            }
        });
    }

    @Test
    public void testLinkWithEmoticonsAndMentions() throws ExtractException {
        String linkWithEmoticonsAndMentions = "@bob @john (success) such a cool feature; " +
                "https://twitter.com/jdorfman/status/430511497475670016";
        extractRequest.extract(linkWithEmoticonsAndMentions,
                new ExtractRequest.ExtractResponseCallback<JSONObject>() {
            @Override
            public void onExtractComplete(final JSONObject response) throws JSONException {
                assertFalse(response == null);
                JSONArray mentionsJsonArray = response.getJSONArray("mentions");
                assertEquals("bob", mentionsJsonArray.getString(0));
                assertEquals("john", mentionsJsonArray.getString(1));

                JSONArray linkJsonArray = response.getJSONArray("links");
                JSONObject linkObject = linkJsonArray.getJSONObject(0);
                assertEquals("https://twitter.com/jdorfman/status/430511497475670016",
                        linkObject.getString("url"));

                JSONArray getEmoticonsJsonArray = response.getJSONArray("emoticons");
                assertEquals("success", getEmoticonsJsonArray.getString(0));
            }

            @Override
            public void onExtractError(final Errors.ExtractError error) {

            }
        });
    }

    @Test
    public void testEmptyStatement() throws ExtractException {
        extractRequest.extract(null, new ExtractRequest.ExtractResponseCallback() {
            @Override
            public void onExtractComplete(final Object response) throws JSONException {
            }

            @Override
            public void onExtractError(final Errors.ExtractError error) {
                assertEquals(error.getErrorType(), Errors.ErrorType.STATEMENT_INVALID);
            }
        });
    }

    @Test
    public void testLinkWithTitle() throws ExtractException {
        String link = "Olympics are starting soon; http://www.nbcolympics.com";
        extractRequest.extract(link,
                new ExtractRequest.ExtractResponseCallback<JSONObject>() {
            @Override
            public void onExtractComplete(final JSONObject response) throws JSONException {
                assertNotNull(response == null);
                JSONArray jsonArray = response.getJSONArray("links");
                JSONObject linkObj = jsonArray.getJSONObject(0);
                assertNotNull(linkObj.getString("title"));
            }

            @Override
            public void onExtractError(final Errors.ExtractError error) {
               assertNotNull(error.getErrorData());
            }
        });
    }
}