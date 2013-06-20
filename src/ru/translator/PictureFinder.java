package ru.translator;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Stack;

public class PictureFinder {
  static String[] get_pictures(String word) {
		String[] links = new String[] {};
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(
					"http://m.images.yandex.ru/search?text="
							+ word.replace(' ', '+'));
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = EntityUtils.toString(httpResponse.getEntity());

				int start = str.indexOf("<div class=" + (char) 34 + "b-results"
						+ (char) 34 + ">");
				int finish = str.indexOf("</div>", start);
				if (finish - start > 23) {
					int n = 0;
					Stack<String> st = new Stack<String>();
					start = str.indexOf("src=", start) + 6;
					while (start != 5 && start < finish && n < 10) {
						int end = str.indexOf((char) 34, start);
						st.push(str.substring(start - 1, end - 1) + "21");
						start = str.indexOf("src=", start) + 6;
						n++;
					}
					links = new String[n];
					for (int i = n - 1; i >= 0; i--)
						links[i] = st.pop();
				}
			}
		} catch (IOException e) {
		}
		return links;
	}
}
