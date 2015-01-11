package com.sti.cabby.parse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;
import com.sti.cabby.MainActivity;
 
public class parse_main extends Activity {
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      //Parse Initialization
        Parse.initialize(this, "mx2a5NYWqa3pyqMcfR2tEmB84lLtWV67BfOaD08h", "NmJZ3KJtrw4cPv6DWNAgD3mJuOPep0cAKI1mOjf2");
 
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
 
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
 
        ParseACL.setDefaultACL(defaultACL, true);
 
        // Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(parse_main.this,
                    LoginSignupActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                Intent intent = new Intent(parse_main.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(parse_main.this,
                        LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        }
 
    }
}
