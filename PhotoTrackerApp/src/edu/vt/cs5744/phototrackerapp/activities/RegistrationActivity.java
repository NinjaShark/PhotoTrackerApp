package edu.vt.cs5744.phototrackerapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import edu.vt.cs5744.phototrackerapp.R;
import edu.vt.cs5744.phototrackerapp.utils.InputValidator;

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
	        
	        //The view has 8 components
	        //this activity has 6 edit text views
	        EditText editUsername = (EditText) findViewById(R.id.textedit_username);
	        EditText editPassword = (EditText) findViewById(R.id.textedit_password);
	        EditText editPasswordVerify = (EditText) findViewById(R.id.textedit_password_verify);
	        EditText editFirstName = (EditText) findViewById(R.id.textedit_firstname);
	        EditText editLastName = (EditText) findViewById(R.id.textedit_lastname);
	        EditText editEmail = (EditText) findViewById(R.id.textedit_email); //this field is not editable by user
	        
	        //this activity has 2 buttons
	        Button buttonSubmit = (Button) findViewById(R.id.button_submit);
	        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
	        
	        //not going to validate username, first, or last name 
	        //[except for SQL injection prevention... aka, no special characters]
	        
	        //password will follow the same validation as in login based on validation in InputValidator
	    
	        
	        

	    }

}
