package com.example.speakingpdf;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FileBrowser extends Activity implements OnItemClickListener {

	ListView list;
	ArrayList<File> files;
	File RDirectory;
	CustomListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_browser);
		list = (ListView) findViewById(R.id.listview);
		list.setOnItemClickListener(this);
		files = new ArrayList<File>();
		RDirectory = new File("/");
		adapter = new CustomListAdapter(this, R.layout.custom_list_item, files);
		list.setAdapter(adapter);
	}

	public void refreshList() {
		files.clear();
		ExtensionFileFilter filter = new ExtensionFileFilter("pdf");
		File[] allfiles = RDirectory.listFiles(filter);

		if (allfiles != null && allfiles.length > 0) {
			for (File f : allfiles)
				files.add(f);
			Collections.sort(files, new FileComparator());
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (RDirectory.getParent() != null) {
			RDirectory = RDirectory.getParentFile();
			refreshList();
			
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshList();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

		File ClickedFile = files.get(position);
		if (ClickedFile.isFile()) {
			Intent extra = new Intent();
			extra.putExtra("file_path", ClickedFile.getAbsolutePath());
			setResult(RESULT_OK, extra);
			finish();
		} else {
			RDirectory = ClickedFile;
			refreshList();
		}

	}

}

class FileComparator implements Comparator<File> {

	@Override
	public int compare(File f1, File f2) {
		// TODO Auto-generated method stub

		if (f1.isDirectory() && f2.isFile())
			return -1;

		if (f2.isDirectory() && f1.isFile())
			return 1;

		return f1.getName().compareToIgnoreCase(f2.getName());
	}

}

class ExtensionFileFilter implements FilenameFilter {

	String filter;

	public ExtensionFileFilter(String filter) {
		this.filter = filter;

	}

	@Override
	public boolean accept(File dir, String filename) {
		// TODO Auto-generated method stub

		if (new File(dir, filename).isDirectory()) {
			return true;
		}

		if (filter != null && filter.length() > 0) {

			return filename.endsWith(filter);
		}

		return false;
	}

}