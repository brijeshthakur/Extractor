package extractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import extractor.model.Emoticons;
import extractor.model.Errors;
import extractor.model.ExtractErrors;
import extractor.model.ExtractModel;
import extractor.model.Link;
import extractor.model.Links;
import extractor.model.Mentions;

/**
 * This class converts extracted data into json. As of now I am using plain json object, but if we
 * see heavy loaded json we can go with GSON.
 */
public final class JsonConverterFactory {

    private static final String MENTIONS = "mentions";
    private static final String EMOTICONS = "emoticons";
    private static final String LINKS = "links";
    private static final String LINK_URL = "url";
    private static final String LINK_TITLE = "title";
    private static final String ERRORS = "errors";
    public static final String ERROR_TYPE = "errorType";
    public static final String ERROR = "error";
    private static JsonConverterFactory jsonConverterFactory = new JsonConverterFactory();

    private JsonConverterFactory() {
    }

    public static JsonConverterFactory getInstance() {
        return jsonConverterFactory;
    }

    public JSONObject toJson(List<ExtractModel> extractList) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (ExtractModel object : extractList) {
            if (object instanceof Mentions) {
                if (!object.get().isEmpty()) {
                    jsonObject.put(MENTIONS, getMentionsJsonArray(object));
                }
            }
            if (object instanceof Emoticons) {
                if (!object.get().isEmpty()) {
                jsonObject.put(EMOTICONS, getEmoticonsJsonArray(object));
                }
            }
            if (object instanceof Links) {
                if (!object.get().isEmpty()) {
                    jsonObject.put(LINKS, getLinksJsonArray(object));
                }
            }

            if (object instanceof ExtractErrors) {
                if (!object.get().isEmpty()) {
                    jsonObject.put(ERRORS, getErrorJsonArray(object));
                }
            }
        }
        return jsonObject;
    }

    private JSONArray getMentionsJsonArray(ExtractModel mentions) {
        JSONArray mentionJsonArray = new JSONArray();
        List<String> mentionList = mentions.get();
        for (String mention : mentionList) {
            mentionJsonArray.put(mention);
        }
        return mentionJsonArray;
    }

    private JSONArray getEmoticonsJsonArray(ExtractModel emoticons) {
        JSONArray emoticonsJsonArray = new JSONArray();
        List<String> emoticonsList = emoticons.get();
        for (String emoticon : emoticonsList) {
            emoticonsJsonArray.put(emoticon);
        }
        return emoticonsJsonArray;
    }

    private JSONArray getLinksJsonArray(ExtractModel links) throws JSONException {
        JSONArray linksJsonArray = new JSONArray();
        List<Link> linkList = links.get();
        for (Link link : linkList) {
            JSONObject linkJsonObject = new JSONObject();
            linkJsonObject.put(LINK_URL, link.getUrl());
            linkJsonObject.put(LINK_TITLE, link.getTitle());
            linksJsonArray.put(linkJsonObject);
        }
        return linksJsonArray;
    }

    private JSONArray getErrorJsonArray(ExtractModel errors) throws  JSONException {
        JSONArray errorsJsonArray = new JSONArray();
        List<Errors.ExtractError> errorsList = errors.get();
        for (Errors.ExtractError extractError : errorsList) {
            JSONObject errorObject = new JSONObject();
            errorObject.put(ERROR_TYPE, extractError.getErrorType());
            errorObject.put(ERROR, extractError.getErrorData());
            errorsJsonArray.put(errorObject);
        }
        return errorsJsonArray;
    }
}
