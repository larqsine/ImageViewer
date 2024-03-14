package utility;

public enum ImageFormat {
        PNG("png"),JPG("jpg");
        private final String value;
        ImageFormat(String value) {
            this.value=value;
        }
        public String getValue() {
            return value;
        }
}
