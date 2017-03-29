package com.example.speakingpdf;

import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;

public class Speaker extends AsyncTask<Void, Integer, Void> {

	TextToSpeech tts;
	Context context;
	TextExtracter extracter;
	final String path;
	final int total;
	private int pagecounter;
	
	private HashMap<String, String> map;

	public Speaker(Context context, String path, String page, TextToSpeech t) {
		this.context = context;
		extracter = new TextExtracter();
		this.path = path;
		total = extracter.getTotalPageInDoc(this.path);
		if(page.equals(""))
			pagecounter=1;
		else
			pagecounter=Integer.parseInt(page);
		tts = t;
		map = new HashMap<String, String>();
		map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "uniqueID");
		
			
		tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

			@Override
			public void onStart(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDone(String arg0) {
				Log.e("Inside background", "next page");
				if (pagecounter <= total) {
					String content=extracter.getText(Speaker.this.path, pagecounter);
					if (content != null) {
						tts.speak(content, TextToSpeech.QUEUE_FLUSH, map);
					} else {
						tts.speak("No Content Found on page " + pagecounter,
								TextToSpeech.QUEUE_FLUSH, map);
					}
					pagecounter++;
				}
				
					

			}
		});

	}

	@Override
	protected void onCancelled(Void result) {
		tts.stop();

		Log.e("Inside background", "Cancelled!");

	}

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		if (pagecounter <= total) {
			String content=extracter.getText(path, pagecounter);
			if (content != null) {
				tts.speak(content, TextToSpeech.QUEUE_FLUSH, map);
			} else {
				tts.speak("No Content Found on page " + pagecounter,
						TextToSpeech.QUEUE_FLUSH, map);
			}
			pagecounter++;
		}
		

			
			

		return null;
	}

}
