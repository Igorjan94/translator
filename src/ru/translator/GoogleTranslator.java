package ru.translator;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.*;

import android.util.Log;

public class GoogleTranslator {
	static String get_translation(String word) {
		String result = new String();
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(
					"http://translate.google.ru/translate_a/t?client=custom&text="
							+ word.replace(' ', '+')
							+ "&hl=ru&sl=en&tl=ru&ie=UTF-8&oe=UTF-8");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = EntityUtils.toString(httpResponse.getEntity());
				try {
					JSONObject jsonObject = new JSONObject(str);
					JSONObject sentences = jsonObject.getJSONArray("sentences")
							.getJSONObject(0);
					result = "было:  " + sentences.getString("orig") + "\n"
						   + "стало: " + sentences.getString("trans");
				} catch (JSONException e) {
					Log.e("GoogleTrnaslate", "Invalid response recieved");
				}
			}
		} catch (java.io.IOException e) {
			Log.e("GoogleTranslate", "Connection failed");
		}
		return result;
	}
}
