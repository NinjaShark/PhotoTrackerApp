package edu.vt.cs5744.phototrackerapp;

import android.app.Activity;
import android.os.Bundle;

/**
 * This activity handles the functionality for user registration.
 * After the user fills out the information and registers, the registration
 * information is sent to the server and an account for the user is made. 
 * 
 * @author Eileen Balci
 *
 */
public class RegistrationActivity extends Activity {
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_register);
	    }

}
