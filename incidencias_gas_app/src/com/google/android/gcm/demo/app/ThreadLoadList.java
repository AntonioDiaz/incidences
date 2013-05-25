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
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ThreadLoadList extends Thread {

	public static final String URL_WEB = CommonUtilities.SERVER_URL + CommonUtilities.SERVER_URL_JSON;
	private Integer myListType;
	
	public ThreadLoadList(Handler handler, Integer listType) {
		super();
		this.handler = handler;
		myListType = listType;
	}

	Handler handler;

	@Override
	public void run() {
		Message message = new Message();
		String jSonStr = doGetPetition();
		if (jSonStr!=null) {
			try {
				Gson gson = new Gson();
				Type type = new TypeToken<ArrayList<Incidence>>(){}.getType();
				List<Incidence> incidences = gson.fromJson(jSonStr, type);
				ActivityList.setIncidences(incidences);
				message.what = 1;
			} catch (Exception e) {
				Log.e(CommonUtilities.TAG, "doGet " + e.getMessage());
				message.what = -1;							
			}
		} else {
			message.what = -1;			
		}
		handler.sendMessage(message);
	}

	private String doGetPetition() {
		try {
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 3000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			int timeoutSocket = 20000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("id_technician", DemoActivity.userId));
			pairs.add(new BasicNameValuePair("list_type", myListType.toString()));			
			String query = URLEncodedUtils.format(pairs, "utf-8");			
			HttpGet httpGet = new HttpGet(URL_WEB + "?" + query);
			

			
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
