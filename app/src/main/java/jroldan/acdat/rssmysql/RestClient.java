package jroldan.acdat.rssmysql;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class
RestClient {
    private static final String APIKEY = "miclaveenclaro";
    //private static final String BASE_URL = "http://192.168.1.100/";
    private static final String BASE_URL = "https://jesusr.alumno.club/";
    private static final int MAX_TIMEOUT = 10000;
    private static final int RETRIES = 3;
    private static final int TIMEOUT_BETWEEN_RETRIES = 5000;
    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", APIKEY);
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.get(getAbsoluteUrl(url), responseHandler);
    }
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", APIKEY);
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", APIKEY);
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", APIKEY);
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void delete(String url, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apikey", APIKEY);
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.delete(getAbsoluteUrl(url), responseHandler);
    }
    public static void cancelRequests(Context c, boolean flag) {
        client.cancelRequests(c, flag);
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
