package com.demo.controlr.services.retorfit;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class FailedValidationResponse implements Serializable {
    @Expose
    private String error;
    @Expose
    private String message;

    public String getError() {
        return error;
    }

    @Expose
    private List<Error> errors;

    public List<Error> getErrors() {
        return errors;
    }

    public static class Error  implements Serializable {
        @Expose
        public String field;

        @Expose
        public String message;
    }

    public String getMessage() {
        return message;
    }
}

