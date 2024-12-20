package com.bemobi.UrlShortener.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliasUtils {

    public static String generateAlias(String url) {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
        String hostname = extractHostname(url);
        return hostname.substring(0, Math.min(4, hostname.length())) + uuid;
    }

    public static boolean isValidUrl(String url) {
        String regex = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(url).matches();
    }

    private static String extractHostname(String url) {
        String regex = "^(?:https?://)?(?:www\\.)?([^/]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            String hostname = matcher.group(1);
            return hostname.replaceAll("[^a-zA-Z0-9]", "");
        }

        return "";
    }

}
