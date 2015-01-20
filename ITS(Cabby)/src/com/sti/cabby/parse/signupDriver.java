package com.sti.cabby.parse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.sti.cabby.MainGUI;
import com.sti.cabby.R;
 
public class signupDriver extends Activity {
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;
    TextView link2;
 
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.signupdriver);
        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
 
        // Locate Buttons in main.xml
        loginbutton = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        link2=(TextView) findViewById(R.id.txtLink);
        
 
        // Sign up Button Click Listener
        signup.setOnClickListener(new OnClickListener() {
 
        	public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();
                final ProgressDialog loading=new ProgressDialog(signupDriver.this);
            	loading.setMessage("Logging in. Please wait. . .");
            	loading.setIndeterminate(true);
            	loading.show();
 
                // Force user to fill up the form
                if (usernametxt.equals("") && passwordtxt.equals("")) {
                	loading.dismiss();
                	AlertDialog.Builder loginPass=new AlertDialog.Builder(signupDriver.this);
                    loginPass.setTitle("eyeTravel Sign up:Driver")
                    	.setMessage("Please fill up username and password fields")
                    	.setCancelable(false)
                    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    			public void onClick(DialogInterface dialog, int id) {
                    					dialog.cancel();
                    			}
                    	})
                    	.create()
                    	.show();
 
                } else {
                    // Save new user data into Parse.com Data Storage
                	loading.dismiss();
                    final ParseUser user = new ParseUser();
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    user.put("type", "driver");
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a dialog message upon successful registration
                            	AlertDialog.Builder loginPass=new AlertDialog.Builder(signupDriver.this);
                                loginPass.setTitle("eyeTravel Sign up:Driver")
                                	.setMessage("Sign up as Driver Successful! Please wait. . .")
                                	.setCancelable(false)
                                	.create()
                                	.show();
                                //logcat current user
                                Log.i("sign up test","Username: "+user.getUsername().toString());
                                Log.i("sign up test", "User type: "+user.get("type").toString());
                                
                                Intent signuplogin=new Intent(signupDriver.this,MainGUI.class);
                                startActivity(signuplogin);
                                finish();
                            } else {
                                //Toast.makeText(getApplicationContext(),"Sign up Error", Toast.LENGTH_LONG).show();
                                AlertDialog.Builder loginPass=new AlertDialog.Builder(signupDriver.this);
                                loginPass.setTitle("eyeTravel Sign up:Driver")
                                	.setMessage("An error occurred. Please try again.")
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
 
            }
        });
        
        //redirect when link is clicked
        link2.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(signupDriver.this, Signup.class);
            startActivity(intent);
            finish();
		}
	});
}
}
