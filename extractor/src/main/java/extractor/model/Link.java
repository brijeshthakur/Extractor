package extractor.model;

public class Link {
        private String url;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(final String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(final String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "{ Link : { url:" + getUrl() + " title:"+ getTitle() + " }";
        }
    }