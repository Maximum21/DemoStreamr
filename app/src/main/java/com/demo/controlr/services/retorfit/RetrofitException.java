package com.demo.controlr.services.retorfit;

import android.text.TextUtils;

import com.demo.controlr.utils.Contains;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException extends RuntimeException {

    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        try {
            if (response == null || response.errorBody() == null) {
                return null;
            }
            String errorBody = response.errorBody().string();

            // Failed Validation
            if (response.code() == 422) {
                try {
                    FailedValidationResponse errorResponse = Contains.GSON.fromJson(errorBody, FailedValidationResponse.class);
                    if (!TextUtils.isEmpty(errorResponse.getError())) {
                        return unexpectedError(new Exception(errorResponse.getError()), null, errorBody);
                    } else if (errorResponse.getErrors() != null && errorResponse.getErrors().size() > 0) {
                        return unexpectedError(new Exception(errorResponse.getErrors().get(0).message), null, errorBody);
                    }
                    return unexpectedError(new Exception(errorResponse.getMessage()), null, errorBody);
                } catch (Exception ex) {
                    ResponseBody errorResponseBody = ResponseBody.create(MediaType.parse("text/json"), errorBody);
                    Converter<ResponseBody, JsonElement> converter = retrofit.responseBodyConverter(JsonElement.class, new Annotation[0]);
                    JsonElement jsonElement = converter.convert(errorResponseBody);
                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (jsonObject.has("errors")) {
                            JsonObject errors = jsonObject.getAsJsonArray("errors").get(0).getAsJsonObject();
                            String message = errors.has("message") ? errors.getAsJsonArray("message").get(0).getAsString() : response.message();
                            return unexpectedError(new Exception(message), null, errorBody);
                        }

                    }
                }
            } else if (response.code() == 500) {
                return unexpectedError(new Exception("Something went wrong!"), null, errorBody);
            }
            ResponseBody errorResponseBody = ResponseBody.create(MediaType.parse("text/json"), errorBody);
            Converter<ResponseBody, JsonElement> converter = retrofit.responseBodyConverter(JsonElement.class, new Annotation[0]);
            JsonElement jsonElement = converter.convert(errorResponseBody);
            String message = "";
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("error")) {
                    try {
                        message = jsonObject.get("error").getAsString();
                    } catch (Exception ex) {
                        JsonElement jsonElement1 = jsonObject.get("error");
                        if (jsonElement1.isJsonObject()) {
                            JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
                            if (jsonObject1.has("message")) {
                                message = jsonObject1.get("message").getAsString();
                            }
                        } else {
                            return unexpectedError(new Exception("Something went wrong!"), response, errorBody);
                        }
                    }
                } else if (jsonObject.has("errors")) {
                    JsonObject errors = jsonObject.getAsJsonArray("errors").get(0).getAsJsonObject();
                    message = errors.has("message") ? errors.getAsJsonArray("message").get(0).getAsString() : response.message();
                } else {
                    if (jsonObject.has("message")) {
                        if (jsonObject.get("message").isJsonNull()) {
                            message = jsonObject.get("message").getAsJsonNull().toString();
                        } else if (jsonObject.get("message").isJsonObject()) {
                            JsonObject msgObject = jsonObject.get("message").getAsJsonObject();
                            if (msgObject.has("error") && msgObject.get("error").isJsonObject()) {
                                JsonObject errorObject = msgObject.get("error").getAsJsonObject();
                                if (errorObject.has("message")) {
                                    message = errorObject.get("message").getAsString();
                                }
                            }
                        } else {
                            message = jsonObject.get("message").getAsString();
                        }
                    } else {
                        message = response.message();
                    }
                }
            } else {
                message = response.message();
//                message = "Something went wrong!";
            }
            return new RetrofitException(message, url, response, null, Kind.HTTP, null, retrofit);
        } catch (Exception e) {
            return unexpectedError(new Exception("The server is under maintenance, please try again few minute later"), null, null);
        }
    }

    public static RetrofitException networkError() {
        return new RetrofitException("No internet connection. Please try again!", null, null, null, Kind.NETWORK, null, null);
    }

    public static RetrofitException unexpectedError(Throwable exception, Response response, String rawResponse) {
        return new RetrofitException(exception.getMessage(), null, response, rawResponse, Kind.UNEXPECTED, exception, null);
    }

    /**
     * Identifies the event kind which triggered a {@link RetrofitException}.
     */
    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    private final String url;
    private final Response response;
    private final String rawResponse;
    private final Kind kind;
    private final Retrofit retrofit;

    private RetrofitException(String message, String url, Response response, String rawResponse, Kind kind, Throwable exception, Retrofit retrofit) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.rawResponse = rawResponse;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    /**
     * The request URL which produced the error.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Response object containing status code, headers, body, etc.
     */
    public Response getResponse() {
        return response;
    }

    public String getRawResponse() { return rawResponse; }

    /**
     * The event kind which triggered this error.
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * The Retrofit this request was executed on
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

}
