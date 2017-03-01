package com.marcaoas.movielist.data.mappers.utils;

import com.marcaoas.movielist.presentation.utils.Logger;

import java.io.IOException;

/**
 * Created by marco on 01/03/17.
 */

public class RequestException extends Exception {

    private static final String NETWORK_ERROR = "NETWORK_ERROR";
    private static final String UNDEFINED = "UNDEFINED";
    private static final String INTERNAL_ERROR = "Internal error";
    private Error error;
    private int code;
    private boolean networkError;
    private String errorMessage;

    public RequestException(int code, String errorMessage) {
        setCode(code);
        setMessage(errorMessage);
    }

    public RequestException(final Throwable cause) {
        super(cause);
        if (cause instanceof IOException) {
            networkError = true;
        }
        Logger.d(cause.toString());
    }

    private void setMessage(String errorMessage) {
        if (errorMessage == null) {
            this.errorMessage = INTERNAL_ERROR;
        } else {
            String error = errorMessage.replace("\"", "");
            this.errorMessage = error.replace("[", "");
        }
    }

    public boolean isHttp() {
        return code > 0;
    }

    public boolean isBadRequest() {
        return code == Error.BAD_REQUEST_CODE.errorCode;
    }

    public boolean isUnauthorized() {
        return code == Error.UNAUTHORIZED_CODE.errorCode || (errorMessage != null && errorMessage.contains("invalid_grant"));
    }

    public boolean isNetworkError() {
        return networkError;
    }

    public boolean isNullObject() {
        return code == Error.NULL_RESULT.errorCode;
    }

    public String getCauseType() {
        if (networkError) return NETWORK_ERROR;
        else if (isHttp()) {
            return error.type;
        }
        return UNDEFINED;
    }

    public void setCode(int code) {
        this.code = code;
        error = Error.fromCode(code);
    }

    public String getErrorMessage() {
        if (errorMessage == null) {
            return UNDEFINED;
        }
        return errorMessage;
    }

    private enum Error {
        NULL_RESULT(1, "NULL_RESULT"),
        BAD_REQUEST_CODE(400, "BAD_REQUEST_CODE"),
        UNAUTHORIZED_CODE(401, "UNAUTHORIZED_CODE"),
        FORBIDDEN(403, "FORBIDDEN");

        int errorCode;
        private String type;

        Error(int errorCode, String type) {
            this.errorCode = errorCode;
            this.type = type;
        }

        public static Error fromCode(int code) {
            for (Error error : Error.values()) {
                if (error.errorCode == code) {
                    return error;
                }
            }
            return NULL_RESULT;
        }
    }
}
