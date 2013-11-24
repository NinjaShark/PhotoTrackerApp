package edu.vt.cs5744.phototrackerapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * This Activity handles the functionality for the login view. This is the
 * first page the user will see when launching the application. The user can
 * log in using their email and password, or create a new new account by registering
 * 
 * @author EileenBalci
 *
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
