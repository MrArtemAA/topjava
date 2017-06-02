package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private final String url;
    private final String cause;
    private final String detail;

    public ErrorInfo(CharSequence url, Throwable ex) {
        this.url = url.toString();
        this.cause = ex.getClass().getSimpleName();
        this.detail = ex.getLocalizedMessage();
    }

    public ErrorInfo(CharSequence url, String message) {
        this.url = url.toString();
        this.cause = "";
        this.detail = message;
    }

}