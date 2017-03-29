package com.example.speakingpdf;

import java.io.File;
import java.util.List;

import com.example.speakingpdf.R;

import android.app.Activity;
import android.content.Context;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<File> {

	Activity context;
	private List files;
	
	public CustomListAdapter(Context context, int resource, List<File> objects) {
		super(context, resource, objects);
		this.context = (Activity)context;
		files=objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = context.getLayoutInflater();
		View rowview = inflater.inflate(R.layout.custom_list_item, null,true);
		TextView fileName = (TextView) rowview.findViewById(R.id.filename);
		ImageView fileImage = (ImageView) rowview.findViewById(R.id.fileimg);
		File current = (File)files.get(position);
		fileName.setText(((File)files.get(position)).getName());
		if(current.isDirectory())
			fileImage.setImageResource(R.drawable.directory);
		else
			fileImage.setImageResource(R.drawable.pdf);
		
		return rowview;
		
		
	}
	
	

}
