package tech.andersonbrito.authazure.infrastructure.filter;

import java.util.Arrays;

public enum AdminUrls {

    HOME("/"),
    TENANT("/tenants");

    private final String url;

    AdminUrls(String url) {
        this.url = url;
    }

    public static boolean toMath(String endpoint) {
        return Arrays.stream(values()).anyMatch(value -> endpoint.endsWith(value.url));
    }
}
