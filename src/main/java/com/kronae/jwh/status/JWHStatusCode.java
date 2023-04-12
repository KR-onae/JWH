package com.kronae.jwh.status;

public record JWHStatusCode(int code) {
    public static final JWHStatusCode SUCCESS = new JWHStatusCode(200);

    public static final JWHStatusCode INFO_100 = new JWHStatusCode(100);
    public static final JWHStatusCode INFO_101 = new JWHStatusCode(101);
    public static final JWHStatusCode INFO_102 = new JWHStatusCode(102);
    public static final JWHStatusCode INFO_103 = new JWHStatusCode(103);
    public static final JWHStatusCode SUCCESS_200 = new JWHStatusCode(200);
    public static final JWHStatusCode SUCCESS_201 = new JWHStatusCode(201);

    public static String getDescription(JWHStatusCode codeObj) {
        int code = codeObj.code;
        return switch (code) {
            default -> null;
            case 100 -> "Continue";
            case 101 -> "Switching Protocol";
            case 102 -> "Processing(WebDAV)";
            case 103 -> "Early Hints";
            case 200 -> "OK";
            case 201 -> "Created";
            case 202 -> "Accepted";
            case 203 -> "Non-Authoritative Information";
            case 204 -> "No Content";
            case 205 -> "Reset Content";
            case 206 -> "Partial Content";
            case 207 -> "Multi-Status";
            case 208 -> "Already Reported";
            case 226 -> "IM Used";
        };
    }
}
