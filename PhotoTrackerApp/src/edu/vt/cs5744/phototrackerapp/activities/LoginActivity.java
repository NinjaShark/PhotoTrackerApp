package edu.vt.cs5744.phototrackerapp.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import edu.vt.cs5744.phototrackerapp.R;

/**
 * This Activity handles the functionality for the login view. This is the first
 * page the user will see when launching the application. The user can log in
 * using their email and password, or create a new new account by registering
 * 
 * @author EileenBalci
 * 
 */
public class LoginActivity extends Activity {

	public static final String TAG = "LOGIN_ACTIVITY"; // for log message tags

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// There are 4 components that the user will be interacting with in this
		// Activity View
		// This activity has two edit text fields: Email and Password
		final EditText editEmail = (EditText) findViewById(R.id.textedit_email);
		final EditText editPassword = (EditText) findViewById(R.id.textedit_password);

		// This activity has two buttons: Sign in and Register
		Button buttonSignIn = (Button) findViewById(R.id.button_sign_in);
		Button buttonRegister = (Button) findViewById(R.id.button_register);
		
		// If the sign-in button is pressed check validity of entered data
		// then if valid, send that data over the service to authenticate against
		// what is in the database
		buttonSignIn.setOnClickListener(new View.OnClickListener() {
			//get the contents of the fields
			String email = editEmail.getText().toString();
			String password = editPassword.getText().toString();
			
            public void onClick(View v) {
            	//both email and password must be valid in order to send 
            	//information for authentication to the server
            	if(validateEmail(email) && validatePassword(password)){
	            	//send it up to the server
	            	serviceConnection(email, password);
            	}else{
            		AlertDialog alertDialog = new AlertDialog.Builder(
        					LoginActivity.this).create();
        			alertDialog.setTitle("Invalid Input");
        			
        			//typically it is best practice to not say which field is the one invalid when it comes
        			//to username/email/passwords to avoid hackers from trying to figure out valid 
        			//e-mails and thus try to crack passwords for those known valid usernames/emails
        			alertDialog.setMessage("Sign In failed: You must enter a valid email or password");
        			alertDialog.setIcon(R.drawable.ic_launcher);
        			alertDialog.show();
            	}
            }
        });
		
		buttonRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//User wants to register, bring up the registration activity
				Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
				startActivity(i); //TODO might want to change to start activity for results?
			}
		});
		
	}
	
	/**
	 * This method checks for the validity of the email
	 * 
	 * LOGIN RULES - Email
	 * 	The email field must have content in order to sign in
	 *	The email must be a well formed email address [check using regex]
	 *
	 * Regex Source: http://www.regular-expressions.info/email.html
	 * 
	 * @param email
	 * @return true if the email is valid
	 */
	private boolean validateEmail(String email){
		/* 
		 * It typically isn't a perfect solution to use regex to validate email, however, since this is
		 * only a prototype, regex was used. For the future, a different method of validating emails should be 
		 * considered. Regex string came from: http://www.regular-expressions.info/email.html
		 */
		 Pattern p = Pattern.compile("\b[A-Z0-9._%+-]+@(?:[A-Z0-9-]+\\.)+[A-Z]{2,4}\b");
		 Matcher m = p.matcher(email);

		//check validity 
		if (!m.matches()) {
			//email is invalid
			return false;
		}else{
			//email is valid
			return true;
		}
	}
	
	/**
	 * This method checks for the validity of the email
	 * 
	 * LOGIN RULES - Password
	 * 	The password field must have content in order to sign in
	 *	The password
	 *		- Must have at least 8 characters
	 *		- Must have at least 1 capital letter
	 *		- Must have at least 1 lowercase letter
	 *		- Must have at least one number or special character
	 *
	 * Regex Source: http://regexlib.com/REDetails.aspx?regexp_id=2204
	 * 
	 * @param password
	 * @return true if the password is valid
	 */
	private boolean validatePassword(String password){
		/* 
		 * No need to re-invent the wheel.
		 * Using this Strong Password regex from: http://regexlib.com/REDetails.aspx?regexp_id=2204
		 * 
		 * "Password must have at least 8 characters with at least one Capital letter, 
		 *  at least one lower case letter and at least one number or special character."
		 */
		 Pattern p = Pattern.compile("(?-i)(?=^.{8,}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z]))((?=(.*\\d){1,})|(?=(.*\\W){1,}))^.*$");
		 Matcher m = p.matcher(password);

		//check validity 
		if (!m.matches()) {
			//password is invalid
			return false;
		}else{
			//password is valid
			return true;
		}
	}

	/**
	 * https://github.com/spring-projects/spring-android
	 */
	private void serviceConnection(String email, String password) {
		// try {
		//
		// String restUrl =
		// "http://phototrackerservice-env.elasticbeanstalk.com/rest/services/registration/register";
		//
		// RestTemplate restTemplate = new RestTemplate();
		// restTemplate.getMessageConverters().add(new
		// MappingJacksonHttpMessageConverter());
		//
		// Map<String, String> request = new HashMap<String, String>();
		// request.put(PASSWORD, password);
		// request.put(EMAIL, email);
		//
		// String response = restTemplate
		// .postForObject(restUrl, request, String.class);
		//
		// Log.d(TAG, response);
		// } catch (ArrayIndexOutOfBoundsException e) {
		// Log.e(TAG, "Arguments Missing for Main Thread: "
		// + e.getMessage());
		// System.exit(-1); //might not be able to do this in Android
		// }
	}

}
