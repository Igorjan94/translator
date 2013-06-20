package ru.translator;

import java.net.URL;

import ru.translator.R;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class QueryResults extends Activity implements Runnable {
  View activity_layout;
	final Handler mainHandler = new Handler();
	final Runnable changeLayout = new Runnable() {
		public void run() {
			setContentView(activity_layout);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_results);
		Thread netThread = new Thread(this);
		netThread.start();
	}

	public ImageView downloadImage(String url) {
		Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
		try {
			URL newurl = new URL(url);
			bitmap = BitmapFactory.decodeStream(newurl.openConnection()
					.getInputStream());
		} catch (Exception ignore) {
			Log.e("downloadImage", ignore.toString());
		}
		ImageView view = new ImageView(this);
		view.setImageBitmap(bitmap);
		return view;
	}

	public void run() {
		String query;
		String translatedQuery;
		query = getIntent().getExtras().getString("query");
		translatedQuery = GoogleTranslator.get_translation(query);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(layoutParams);
		LinearLayout.LayoutParams elementParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		
		TextView textView = new TextView(this);
		textView.setText(translatedQuery);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setLayoutParams(elementParams);
		layout.addView(textView);
		
		String[] picture_urls = PictureFinder.get_pictures(query);
		for (int i = 0; i < picture_urls.length; ++i) {
			layout.addView(downloadImage(picture_urls[i]));
		}
		ScrollView scroll = new ScrollView(this);
		scroll.addView(layout);
		scroll.setLayoutParams(layoutParams);
		activity_layout = scroll;
		mainHandler.post(changeLayout);
	}
}
