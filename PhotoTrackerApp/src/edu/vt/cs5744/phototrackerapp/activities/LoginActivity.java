package edu.vt.cs5744.phototrackerapp.activities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import edu.vt.cs5744.phototrackerapp.R;
import edu.vt.cs5744.phototrackerapp.utils.DatabaseMapping;

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

	private boolean authenticated = false;
	
	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// There are 4 components that the user will be interacting with this
		// View
		// This activity has two edit text fields: Email and Password
		final EditText editUsername = (EditText) findViewById(R.id.textedit_username);
		final EditText editPassword = (EditText) findViewById(R.id.textedit_password);

		// This activity has two buttons: Sign in and Register
		Button buttonSignIn = (Button) findViewById(R.id.button_sign_in);
		Button buttonRegister = (Button) findViewById(R.id.button_register);

		// If the sign-in button is pressed check validity of entered data
		// then if valid, send that data over the service to authenticate
		// against
		// what is in the database
		buttonSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// get the latest contents of the fields
				username = editUsername.getText().toString();
				password = editPassword.getText().toString();
				
				// TODO might want to still sanitize the username and password
				// fields before authentication

				// need to authenticate email and password combination from
				// server. This authenticates against what is stored in the database
				new NetworkConnections().execute("", "", "");

				//Was the user Authenticated?
				if (authenticated) {
					Intent i = new Intent(LoginActivity.this,
											MappingActivity.class);
					i.putExtra("username", getUsername());
					startActivity(i);
				} else {
					Log.d(TAG, "Invalid Username: " 
								+ getUsername() 
								+ " and Password: " 
								+ getPassword()); //for debugging
					
					AlertDialog alertDialog = new AlertDialog.Builder(
							LoginActivity.this).create();
					alertDialog.setTitle("Authentication Faliure");
					alertDialog
							.setMessage("The Username and Password combination entered was not found");
					alertDialog.setIcon(R.drawable.ic_launcher);
					alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing
								}
							});

					alertDialog.show();
				}

			}
		});

		buttonRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// User wants to register, bring up the registration activity
				Intent i = new Intent(LoginActivity.this,
						RegistrationActivity.class);
				startActivity(i); // TODO might want to change to start activity
									// for results?
			}
		});

	}

	/**
	 * @return the email
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Handle network connections in an AsyncTask
	 * 
	 * @author Eileen Balci
	 */
	private class NetworkConnections extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String result = serviceConnection(getUsername(), getPassword());
			
			//Check the result from the server 
			if (result.equalsIgnoreCase("Authenticated")) {
				LoginActivity.this.authenticated = true;
			} else {
				LoginActivity.this.authenticated = false;
			}
			
			return result;
		}

		/**
		 * https://github.com/spring-projects/spring-android
		 */
		private String serviceConnection(String username, String password) {
			try {

				String restUrl = "http://phototrackerservice-env.elasticbeanstalk.com/rest/services/login/login";

				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new MappingJacksonHttpMessageConverter());

				Map<String, String> request = new HashMap<String, String>();
				request.put(DatabaseMapping.PASSWORD, password);
				request.put(DatabaseMapping.USERNAME, username);

				String response = restTemplate.postForObject(restUrl, request,
						String.class);

				Log.d(TAG, response); //For debugging

				return response;

			} catch (ArrayIndexOutOfBoundsException e) {
				Log.e(TAG,
						"Arguments Missing for Main Thread: " + e.getMessage());
			}

			return "No Response";
		}

	}

}
