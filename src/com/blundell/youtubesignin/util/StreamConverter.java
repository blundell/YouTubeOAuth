package com.blundell.youtubesignin.util;

import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

public class StreamConverter {

	public static JSONObject convertStreamToJsonObject(InputStream inputStream) throws JSONException {
		return new JSONObject(convertStreamToString(inputStream));
	}

    public static String convertStreamToString(InputStream inputStream) {
        if (inputStream != null) {
        	try {
	            Writer writer = new StringWriter();

	            char[] buffer = new char[1024];
	            try {
	                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"),1024);
	                int n;
	                while ((n = reader.read(buffer)) != -1) {
	                    writer.write(buffer, 0, n);
	                }
	            } catch (UnsupportedEncodingException e) {
					Log.e("convertStreamToString", e);
				} finally {
	                inputStream.close();
	            }
	            return writer.toString();
        	} catch (IOException e) {
        		Log.e("IOException returning blank string", e);
				return "";
			}
        } else {
            return "";
        }
    }

	public static byte[] convertStreamToByteArray(InputStream is) {
		if(is != null){
			try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int length = 0;
				while ((length = is.read(buffer)) != -1 ){
					outputStream.write(buffer, 0, length);
				}
				return outputStream.toByteArray();
			} catch (IOException e) {
				Log.e("IOException returning blank array", e);
				return new byte[0];
			}
		} else {
			return new byte[0];
		}
	}
}
