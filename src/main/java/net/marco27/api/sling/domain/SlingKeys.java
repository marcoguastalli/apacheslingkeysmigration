package net.marco27.api.sling.domain;

public class SlingKeys {

    private String primaryType = "sling:MessageEntry";
    private String key;
    private String message;

    public SlingKeys(final String key, final String message) {
        this.key = key;
        this.message = message;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}
