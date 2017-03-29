package com.example.speakingpdf;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ControlScreen extends Activity implements OnClickListener {

	TextView path;
	Button browse, start;
	EditText etpNo;
	TextToSpeech tts;
	boolean playorpause;
	
	private Speaker speaker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controlscr);
		path = (TextView) findViewById(R.id.path);
		browse = (Button) findViewById(R.id.browse);
		browse.setOnClickListener(this);
		start = (Button) findViewById(R.id.bStart);
		
		etpNo = (EditText) findViewById(R.id.etpageno);
		start.setOnClickListener(this);
		
		tts = new TextToSpeech(this, new OnInitListener() {

			@Override
			public void onInit(int status) {
				// TODO Auto-generated method stub
				if (status != TextToSpeech.ERROR) {
					tts.setLanguage(Locale.ENGLISH);
					
					tts.setSpeechRate(0.85f);
					Log.e("Speaker", "Onint");

				}
			}

		});
		playorpause = true;

	}

	@Override
	public void onClick(View v) {
		if (v == browse) {
			Intent intent = new Intent(this, FileBrowser.class);
			startActivityForResult(intent, 1);
			return;
		} else if (v == start) {
			

			if (playorpause) {
				start.cancelLongPress();
				
				
					speaker = new Speaker(this, path.getText().toString(),
							etpNo.getText().toString(), tts);
				
				speaker.execute();
				playorpause = !playorpause;
				start.setText("pause");

			} else {

				Log.e("Inside background", "Cancelling....");
				if (!speaker.isCancelled()) {
					tts.stop();
					speaker.cancel(true);
					speaker = null;
				}
				
				
				playorpause = !playorpause;
				start.setText("Start");
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				if (data.hasExtra("file_path")) {
					path.setText(data.getStringExtra("file_path"));
				}
			}
		}
	}

	@Override
	protected void onPause() {
		tts.stop();
		if (speaker != null && !speaker.isCancelled())
			speaker.cancel(true);
				start.setText("Start");
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

}
