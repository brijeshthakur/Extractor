package extractor;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import extractor.exception.ExtractException;
import extractor.model.Emoticons;
import extractor.model.Errors;
import extractor.model.ExtractErrors;
import extractor.model.ExtractModel;
import extractor.model.Link;
import extractor.model.Links;
import extractor.model.Mentions;

/**
 * <p>
 * This class extracts data in request/response pattern. To make it thread-safe, every time a new
 * request should be created to extract data from statement.
 * </p>
 * <b>Example : </b>
 * <pre>
 * {@code
 * ExtractRequest extractRequest = new ExtractRequest();
 * extractRequest.extract(statement, new ExtractRequest.ExtractResponseCallback<JSONObject>() {
 * @Override
 * public void onExtractComplete(final JSONObject response) throws JSONException {
 * }
 * @Override
 * public void onExtractError(final Errors.ExtractException error)
 * });
 * }
 * </pre>
 */
public class ExtractRequest {
    private static String TAG = "ExtractRequest";
    private WeakReference<ExtractResponseCallback> extractResponseCallbackRef;

    public void extract(String statement, ExtractResponseCallback extractorResponseCallback)
            throws ExtractException {
        if (extractorResponseCallback == null) {
            throw new ExtractException("callback can't be null");
        }
        extractResponseCallbackRef = new WeakReference(extractorResponseCallback);
        if (TextUtils.isEmpty(statement)) {
            onExtractRequestError(new Errors.ExtractError(Errors.ErrorType.STATEMENT_INVALID,
                    Extractor.EMPTY_STATEMENT_ERROR));
            return;
        }
        ExtractTask extractTask = new ExtractTask(this);
        extractTask.execute(statement);
    }

    private void onExtractRequestComplete(List<ExtractModel> extractList) {
        ExtractResponseCallback extractResponseCallback = extractResponseCallbackRef.get();
        if (extractResponseCallback == null) {
            Log.e(TAG, "It seems callback got GCed. Is callback is a type of anonymous class?");
            return;
        }
        try {
            JsonConverterFactory jsonConverterFactory = JsonConverterFactory.getInstance();
            JSONObject extractJson = jsonConverterFactory.toJson(extractList);
            extractResponseCallback.onExtractComplete(extractJson);
        } catch (JSONException e) {
            extractResponseCallback.onExtractError(new Errors.ExtractError(Errors.ErrorType.INTERNAL_ERROR,
                    e.getMessage()));
        }
    }

    private void onExtractRequestError(Errors.ExtractError extractError) {
        ExtractResponseCallback extractResponseCallback = extractResponseCallbackRef.get();
        if (extractResponseCallbackRef.get() == null) {
            Log.e(TAG, "It seems callback got GCed. Is callback is a type of anonymous class?");
            return;
        }
        extractResponseCallback.onExtractError(extractError);
    }

    public interface ExtractResponseCallback<T> {
        void onExtractComplete(T response) throws JSONException;
        void onExtractError(Errors.ExtractError error);
    }

    private static class ExtractTask extends AsyncTask<String, Void, List<ExtractModel>> {

        ExtractRequest extractRequest;

        public ExtractTask(ExtractRequest extractRequest) {
            this.extractRequest = extractRequest;
        }

        @Override
        protected List<ExtractModel> doInBackground(final String... params) {
            String statement = params[0];
            List<ExtractModel> extractList = new ArrayList<>();
            ExtractErrors extractErrors = new ExtractErrors();
            try {
                // Extract Mention
                Extractor<Mentions> mentionExtractor = new MentionExtractor();
                extractList.add(mentionExtractor.extract(statement));
            } catch (ExtractException mentionExtractException) {
                extractErrors.add(new Errors.ExtractError(Errors.ErrorType.MENTION_EXTRACT_ERROR,
                        mentionExtractException.getMessage()));
            }

            try {
                // Extract Emoticons
                Extractor<Emoticons> emoticonsExtractor = new EmoticonsExtractor();
                extractList.add(emoticonsExtractor.extract(statement));
            } catch (ExtractException emoticonsException) {
                extractErrors.add(new Errors.ExtractError(Errors.ErrorType.EMOTICONS_EXTRACT_ERROR,
                        emoticonsException.getMessage()));
            }

            try {
                // Extract Links
                Extractor<Links> linkExtractor = new LinkExtractor();
                Links links = linkExtractor.extract(statement);
                extractList.add(links);
                if (!Utility.isConnected(ExtractApplication.getContext())) {
                    extractErrors.add(new Errors.ExtractError(Errors.ErrorType.NO_NETWORK,
                            "Not connected to internet"));
                    extractList.add(extractErrors);
                    return extractList;
                }
                for (Link link : links.get()) {
                    try {
                        String url = link.getUrl();
                        String linkTitle = LinkPreviewRequest.getTitle(url);
                        link.setTitle(linkTitle);
                    } catch (IOException e) {
                        extractErrors.add(new Errors.ExtractError(Errors.ErrorType.LINKS_EXRACT_ERROR,
                                e.getMessage()));
                    }

                }
            } catch (ExtractException extractException) {
                extractErrors.add(new Errors.ExtractError(Errors.ErrorType.LINKS_EXRACT_ERROR,
                        extractException.getMessage()));
            }
            extractList.add(extractErrors);
            return extractList;
        }

        @Override
        protected void onPostExecute(final List<ExtractModel> extractList) {
            super.onPostExecute(extractList);
            if (extractRequest == null) {
                return;
            }
            extractRequest.onExtractRequestComplete(extractList);
        }
    }
}
