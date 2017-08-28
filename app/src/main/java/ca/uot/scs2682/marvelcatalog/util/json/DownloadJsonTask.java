package ca.uot.scs2682.marvelcatalog.util.json;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ricardohidekiyamamoto on 2017-03-25.
 */

public class DownloadJsonTask extends AsyncTask<String, Void, JSONArray> {

    public static final String PUBLIC_KEY = "29908bee18c47b50de2c27902ab2d9bf";
    public static final String PRIVATE_KEY = "5d5f0c9b3d26d9f8a2c368ff0bea3504c2123f71";


    @NonNull
    private final WeakReference<JsonConsumer> appActivityWeakReference;

    public DownloadJsonTask(@NonNull JsonConsumer jsonConsumer){
        appActivityWeakReference = new WeakReference<JsonConsumer>(jsonConsumer);
    }



    @Override
    protected JSONArray doInBackground(String... urls) {
        String urlString = urls != null && urls.length > 0 ? urls[0] : "";
        if(TextUtils.isEmpty(urlString)){
            // supplied url is empty or null
            return null;
        }

        InputStream inputStream = null;
        JSONArray jsonArray = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();

            int statusCode = connection.getResponseCode();
            String statusMessage = connection.getResponseMessage();

            Log.w("DownloadJsonTask", "statusCode=" + statusCode + ", statusMessage=" + statusMessage);

            //get stream from the remote place

            inputStream = connection.getInputStream();

            //convert stream into a string
            String jsonString = new Scanner(inputStream, "UTF-8")
                    .useDelimiter("\\A")
                    .next();

            connection.disconnect();
            JSONObject json = new JSONObject(jsonString);
            String status = json.optString("status","");
            int code = json.optInt("code",-1);
            JSONObject dataObject = json.optJSONObject("data");

            if ("Ok".equals(status) && code == 200 && dataObject !=null ) {
                jsonArray = dataObject.optJSONArray("results");
            }

        } catch (JSONException | IOException e){
            e.printStackTrace();
            JSONObject object = null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException | NullPointerException e){
                // not interested
            }
        }
        return jsonArray;
    }


    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        if (appActivityWeakReference.get() != null){
            appActivityWeakReference.get().updateData(jsonArray);
        }
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
