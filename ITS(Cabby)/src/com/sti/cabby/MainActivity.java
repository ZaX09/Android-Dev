package com.sti.cabby;

import com.sti.cabby.parse.parse_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Intent login =new Intent(this,parse_main.class);
	        startActivity(login);
	        finish();
	 }

	 private static long back_pressed;

	 @Override
	 public void onBackPressed()
	 {
	         if (back_pressed + 2000 > System.currentTimeMillis()) 
	                super.onBackPressed();
	         else 
	              Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
	         back_pressed = System.currentTimeMillis();
	 }
	 

}
