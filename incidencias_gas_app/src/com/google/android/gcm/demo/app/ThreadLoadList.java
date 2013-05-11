package com.google.android.gcm.demo.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ThreadLoadList extends Thread {

	public static final String URL_WEB = CommonUtilities.SERVER_URL + CommonUtilities.SERVER_URL_JSON;
	
	public ThreadLoadList(Handler handler) {
		super();
		this.handler = handler;
	}

	Handler handler;

	@Override
	public void run() {
		String jSonStr = doGetPetition();
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Incidence>>(){}.getType();
		List<Incidence> incidences = gson.fromJson(jSonStr, type);
		ActivityList.setIncidences(incidences);
		handler.sendMessage(new Message());
	}

	private String doGetPetition() {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = null;
			httpGet = new HttpGet(URL_WEB);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String str = convertStreamToString(entity.getContent());
			return str;
		} catch (IOException e) {
			Log.e(CommonUtilities.TAG, "doGet " + e.getMessage());
			return null;
		}
	}

	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8 * 1024);
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
