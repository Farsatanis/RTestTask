package framework.webdriver;

public enum  Browser {

    CHROME(Constants.CHROME),
    FIREFOX(Constants.FIREFOX);

    private String _value;

    Browser(String value) {
        _value = value;
    }

    public String getValue() {
        return _value;
    }

    public static Browser fromString(String text) {
        for (Browser b : values()) {
            if (b.getValue().equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException(
                "No enum constant Browser found for argument: "  + text);
    }

    public interface Constants {
        String CHROME = "chrome";
        String FIREFOX = "firefox";
    }
}
