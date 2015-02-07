package com.sti.cabby;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class autocomplete extends FragmentActivity{
	String url;
	private static final String TAG_RESULT = "predictions";
	JSONObject json;
	JSONArray contacts = null;
	AutoCompleteTextView ed;
	String[] search_text;
	ArrayList<String> names;
	ArrayAdapter<String> adp;
	String browserKey="AIzaSyCR1h3FmJ1D-rih12hV0xzIcYgWFggSZ7I";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directions_input);
		ed=(AutoCompleteTextView)findViewById(R.id.from);
		ed.setThreshold(0);
		names=new ArrayList<String>();
			ed.addTextChangedListener(new TextWatcher()
		  {

		   public void afterTextChanged(Editable s)
		   {

		   }

		   public void beforeTextChanged(CharSequence s, int start,
		    int count, int after)
		   {

		   }

		   public void onTextChanged(CharSequence s, int start,
		    int before, int count)
		   {
			   
			   search_text= ed.getText().toString().split(",");
			   url="https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+search_text[0]+"&location=37.76999,-122.44696&radius=500&sensor=true&key="+browserKey;
			   if(search_text.length<=1){
				   names=new ArrayList<String>();
				   Log.d("URL",url);
					paserdata parse=new paserdata();
					parse.execute();
					Log.i("autocomplete", "Parsing JSON");
			   }
			 
		   }
		  });
			
	}
	public class paserdata extends AsyncTask<Void, Integer, Void>{
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			
			JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			 json = jParser.getJSONFromUrl(url.toString());
			if(json !=null)
			{
			try {
				// Getting Array of Contacts
				contacts = json.getJSONArray(TAG_RESULT);
				
				for(int i = 0; i < contacts.length(); i++){
					JSONObject c = contacts.getJSONObject(i);
					String description = c.getString("description");
					Log.d("description", description);
					names.add(description);
				
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			}
			
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			adp = new ArrayAdapter<String>(getApplicationContext(), 
				    android.R.layout.simple_list_item_1, names) {
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
				    View view = super.getView(position, convertView, parent);
				    TextView text = (TextView) view.findViewById(android.R.id.text1);
				      text.setTextColor(Color.BLACK);
				    return view;
				  }
				};
			
			
			ed.setAdapter(adp);	

		
		}
		}

	
}
