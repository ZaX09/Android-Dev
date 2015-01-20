package com.sti.cabby.parse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.sti.cabby.MainGUI;
import com.sti.cabby.R;
 
public class Login extends Activity {
    // Declare Variables
    Button loginbutton, signup;
    String usernametxt, passwordtxt;
	View offlinetxt;
    EditText password, username;
 
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.loginsignup);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        offlinetxt=findViewById(R.id.offline);
 
        // Locate Buttons in main.xml
        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
 
        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {
 
            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();
                final ProgressDialog loading=new ProgressDialog(Login.this);
            	loading.setMessage("Logging in. Please wait. . .");
            	loading.setIndeterminate(true);
            	loading.show();
                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(Login.this, MainGUI.class);
                                    loading.dismiss();
                                    startActivity(intent);
                                    AlertDialog.Builder loginPass=new AlertDialog.Builder(Login.this);
                                    loginPass.setTitle("eyeTravel Log-in")
                                    	.setMessage("Login Successful! Please wait. . .")
                                    	.setCancelable(false)
                                    	.create()
                                    	.show();
                                    finish();
                                } else {
                                    /*Toast.makeText(getApplicationContext(),"No such user exist, please signup",Toast.LENGTH_LONG).show();*/
                                	loading.dismiss();
                                	AlertDialog.Builder loginPass=new AlertDialog.Builder(Login.this);
                                    loginPass.setTitle("eyeTravel Log-in")
                                    	.setMessage("No such user exist, please signup")
                                    	.setCancelable(false)
                                    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    			public void onClick(DialogInterface dialog, int id) {
                                    					dialog.cancel();
                                    			}
                                    	})
                                    	.create()
                                    	.show();
                                }
                            }
                        });
            }
        });
        // Sign up Button Click Listener
        signup.setOnClickListener(new OnClickListener() {
 
            public void onClick(View arg0) {
            	Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
                //finish();
            }
        });
        
        //offline mode
        offlinetxt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Login.this, MainGUI.class);
                startActivity(intent);
                finish();
			}
		});
 
    }
}
