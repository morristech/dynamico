package org.ecloga.dynamico.network;

import android.content.Context;
import android.os.AsyncTask;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.ecloga.dynamico.R;
import org.ecloga.dynamico.Util;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;

public abstract class ApiRequest extends AsyncTask<Void, Void, String> {

    protected String url, output, error;
    protected Context context;
    protected Response response;

    private OkHttpClient client;
    private ApiResponse handler;

    protected ApiRequest() {
        client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .build();
    }

    protected void executeRequest() {
        Request request;

        request = requestBuilder().get().build();

        try {
            response = client.newCall(request).execute();

            output = response.body().string();

            int responseCode = response.code();

            Util.log("API", url + " => " + responseCode + ": " + output);

            if (responseCode < 200 || responseCode > 299) {
                JSONObject jsonObject = new JSONObject(output);

                if (jsonObject.has("error")) {
                    JSONObject errorObject = jsonObject.getJSONObject("error");
                    error = errorObject.getString("message");
                }

                cancel(true);
            }
        } catch (Exception e) {
            error = e.getMessage();
            Util.log("API error", url + " : " + error);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Util.blockTouches(context);

        if(!Util.hasNetworkAccess(context)) {
            error = context.getResources().getString(R.string.error_disconnected);
            cancel(true);
        }
    }

    @Override
    protected String doInBackground(Void... arg0) {
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        finish();
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);

        finish();
    }

    private void finish() {
        Util.allowTouches(context);

        handle();
    }

    public ApiRequest addHandler(ApiResponse responseHandler) {
        this.handler = responseHandler;

        return this;
    }

    public void send() {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void handle() {
        if(handler != null) {
            if(error != null) {
                handler.onError(error);
            }else {
                handler.onSuccess(output);
            }
        }
    }

    private Request.Builder requestBuilder() {
        return new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json");
    }
}