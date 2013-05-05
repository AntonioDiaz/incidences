package com.google.android.gcm.demo.app;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityList extends ListActivity {
	
	String testValues[] = new String[] { "URJC", "uUC3M", "UPMm" };
	static List<Incidence> list;
	private Context mContext;
	private ProgressDialog pd; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.incidences);
		mContext = this;
		pd = ProgressDialog.show(this, "incidencias", "Cargando datos...", true, false);
		Thread myThread = new ThreadLoadList(myHandler);		
		myThread.start();
	}

	
	
	
	//Declaracion de la clase Handler.
	Handler myHandler = new Handler (new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			Toast.makeText(mContext, "zas", Toast.LENGTH_SHORT).show();
			String[] listTemp = new String[list.size()];
			int i = 0;
			for (Incidence incidence : list) {
				listTemp[i++] = incidence.getContactName();
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, listTemp);
			ListView listView = (ListView) findViewById(android.R.id.list);
			listView.setAdapter(arrayAdapter);
			pd.dismiss();
			return true;			
		}
	});
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(this, "Hola " + testValues[position], Toast.LENGTH_SHORT).show();
	}

	public List<Incidence> getList() {
		return list;
	}

	public void setList(List<Incidence> list) {
		this.list = list;
	}

}
