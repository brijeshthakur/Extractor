package extractor.model;

public class Errors {

    public enum ErrorType {

        NO_ERROR("No Error"),
        STATEMENT_INVALID("Invalid Statement"),
        INTERNAL_ERROR("Internal Error"),
        NO_NETWORK("No Internet Connection"),
        MENTION_EXTRACT_ERROR("Mention Extract Error"),
        LINKS_EXRACT_ERROR("Link Extract Error"),
        EMOTICONS_EXTRACT_ERROR("Emoticons Extract Error");
        String error;

        ErrorType(String error) {
            this.error = error;
        }
    }

    public static class ExtractError {

        private ErrorType errorType;
        private String data;

        public ExtractError(ErrorType errorType, String data) {
            this.errorType = errorType;
            this.data = data;
        }

        public String getErrorData() {
            return data;
        }

        public ErrorType getErrorType() {
            return errorType;
        }

    }
}
