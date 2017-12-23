package com.example.androidlibrary;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    private static final String TAG = "HttpUtils ";

    public static void httpGet(String host, String path, HashMap<String, String> params, Callback callback) {
        checkNotNull(host,"host 不能为空");
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("?");
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuilder.append(entry.getKey()).append("=").
                            append(entry.getValue()).
                            append("&");
                }
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            URL url = new URL(host + "/" + path + stringBuilder.toString());
            Log.i(TAG, "httpGet: url :" + (host + path + stringBuilder.toString()));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                String str = getStringFromByte(readBAFromInputStream(connection.getInputStream()));
                if (callback != null) {
                    callback.onSuccess(str);
                }
            } else if (responseCode == 302 || responseCode == 301) { //redirect from http to https
                String location = connection.getHeaderField("Location");
                connection.disconnect();
                Log.i(TAG, "location " + location);
                HttpURLConnection connection2 = (HttpURLConnection) new URL(location).openConnection();
                connection2.setConnectTimeout(5000);
                connection2.setReadTimeout(5000);
                connection2.setRequestMethod("GET");
                connection2.connect();

                int responseCode2 = connection2.getResponseCode();
                if (responseCode2 == 200) {
                    String str2 = getStringFromByte(readBAFromInputStream(connection2.getInputStream()));
                    if (callback != null) {
                        callback.onSuccess(str2);
                    }
                } else {
                    if (callback != null) {
                        callback.onFailed("http responseCode = " + responseCode2);
                    }
                }
            } else {
                if (callback != null) {
                    callback.onFailed("http responseCode = " + responseCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onFailed("http IOException = " + e.getMessage());
            }
        }
    }

    public static void httpPost(String host, String path, HashMap<String, String> params, Callback callback) {
        checkNotNull(host,"host 不能为空");
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(host + "/" + path).openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.connect();

            StringBuilder stringBuilder = new StringBuilder();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuilder.append(entry.getKey()).append("=").
                            append(URLEncoder.encode(entry.getValue(), "UTF-8")).
                            append("&");
                }
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(stringBuilder.toString().getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                String str = getStringFromByte(readBAFromInputStream(connection.getInputStream()));
                if (callback != null) {
                    callback.onSuccess(str);
                }
            } else if (responseCode == 302 || responseCode == 301) {
                String location = connection.getHeaderField("Location");
                connection.disconnect();
                HttpURLConnection connection2 = (HttpURLConnection) new URL(location).openConnection();
                connection2.setRequestMethod("POST");
                connection2.setReadTimeout(5000);
                connection2.setConnectTimeout(5000);
                connection2.setDoOutput(true);
                connection2.setRequestProperty("Connection", "keep-alive");
                connection2.setRequestProperty("Charset", "UTF-8");
                connection2.connect();

                int responseCode2 = connection2.getResponseCode();
                if (responseCode2 == 200) {
                    String str2 = getStringFromByte(readBAFromInputStream(connection2.getInputStream()));
                    if (callback != null) {
                        callback.onSuccess(str2);
                    }
                } else {
                    if (callback != null) {
                        callback.onFailed("http responseCode = " + responseCode2);
                    }
                }
            } else {
                if (callback != null) {
                    callback.onFailed("http responseCode = " + responseCode);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onFailed("http IOException = " + e.getMessage());
            }
        }
    }

    private static byte[] readBAFromInputStream(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }

    private static String getStringFromByte(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public interface Callback {

        void onSuccess(String str);

        void onFailed(String str);

    }
}
