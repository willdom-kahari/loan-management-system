package io.github.willdomkahari.loanmanagement.api;

public record ApiResponseWrapper<T>(boolean success, String message, T data) {

    public static <T> ApiResponseWrapper<T> success(String message) {
        return new ApiResponseWrapper<>(true, message, null);
    }

    public static <T> ApiResponseWrapper<T> success(String message, T data) {
        return new ApiResponseWrapper<>(true, message, data);
    }

    public static <T> ApiResponseWrapper<T> failed(String message) {
        return new ApiResponseWrapper<>(false, message, null);
    }

    public static <T> ApiResponseWrapper<T> failed(String message, T data) {
        return new ApiResponseWrapper<>(false, message, data);
    }
}