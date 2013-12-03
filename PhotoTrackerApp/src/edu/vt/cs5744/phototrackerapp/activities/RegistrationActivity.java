package edu.vt.cs5744.phototrackerapp.activities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import edu.vt.cs5744.phototrackerapp.R;
import edu.vt.cs5744.phototrackerapp.utils.DatabaseMapping;
import edu.vt.cs5744.phototrackerapp.utils.InputValidator;

/**
 * This activity handles the functionality for user registration. After the user
 * fills out the information and registers, the registration information is sent
 * to the server and an account for the user is made.
 * 
 * @author Eileen Balci
 * 
 */
public class RegistrationActivity extends Activity {
	
	private final static String TAG = "REGISTRATION_ACTIVITY";
	private final boolean enableDebug = false;
	
	private String invalidInputMessage = "";
	//String values
	private String username;
	private String password;
	private String passwordVerify;
	private String firstName;
	private String lastName;
	private String email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// The view has 8 components
		// this activity has 6 edit text views
		final EditText editUsername = (EditText) findViewById(R.id.textedit_username);
		final EditText editPassword = (EditText) findViewById(R.id.textedit_password);
		final EditText editPasswordVerify = (EditText) findViewById(R.id.textedit_password_verify);
		final EditText editFirstName = (EditText) findViewById(R.id.textedit_firstname);
		final EditText editLastName = (EditText) findViewById(R.id.textedit_lastname);
		final EditText editEmail = (EditText) findViewById(R.id.textedit_email);

		// this activity has 2 buttons
		Button buttonSubmit = (Button) findViewById(R.id.button_submit);
		Button buttonCancel = (Button) findViewById(R.id.button_cancel);
		
		//For DEBUG only - only pre-fills if the enableDebug is set to true, to disable set to false
		String[] testValues = debugValues(enableDebug);
		if(testValues != null){
			editUsername.setText(testValues[0]);
			editPassword.setText(testValues[1]);
			editPasswordVerify.setText(testValues[2]);
			editFirstName.setText(testValues[3]);
			editLastName.setText(testValues[4]);
			editEmail.setText(testValues[5]);			
		}

		// not going to validate username, first, or last name
		// [except for SQL injection prevention... aka, no special characters]

		buttonSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Need to make sure we get the latest input from the user
				username = editUsername.getText().toString();
				password = editPassword.getText().toString();
				passwordVerify = editPasswordVerify.getText().toString();
				firstName = editFirstName.getText().toString();
				lastName = editLastName.getText().toString();
				email = editEmail.getText().toString();
				
				// validate fields
				// if all passes, send info to server!
				if(checkFields(getPassword(), getPasswordVerify(), getEmail(), getUsername(), getFirstName(), getLastName())){
					//send data to the server
					new NetworkConnections().execute("", "", "");
					
					AlertDialog alertDialog = new AlertDialog.Builder(
        					RegistrationActivity.this).create();
        			alertDialog.setTitle("Registration Complete");
        			alertDialog.setMessage("Thank You for Registering, " + getUsername());
        			alertDialog.setIcon(R.drawable.ic_launcher);
        			alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							RegistrationActivity.this.finish(); //finish the registration, go back to login
						}
					});
        			
        			alertDialog.show();
				}else{
					AlertDialog alertDialog = new AlertDialog.Builder(
        					RegistrationActivity.this).create();
        			alertDialog.setTitle("Invalid Input");
        			
        			//alerts the user on what input value was invalid so they can make corrections
        			alertDialog.setMessage(getInvalidInputMessage());
        			alertDialog.setIcon(R.drawable.ic_launcher);
        			alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// do nothing
							
						}
					});
        			
        			alertDialog.show();
				}
			}
		});

		buttonCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// go back to the login page
				RegistrationActivity.this.finish();
			}
		});

	}

	/**
	 * Check the required registration fields for valid input
	 * 
	 * @param password
	 * @param passwordVerify
	 * @param email
	 * @param username
	 * @return true if all checks pass
	 */
	private boolean checkFields(String password, String passwordVerify, String email, String username, String first, String last) {
		final int numToMeetChecks = 4; //there are 4 total checks that must pass
		int validChecker = 0; //4 checks, if this variable has a value of 4 after all checks, all is valid
		boolean allIsValid = false; // by default, initialization as false.

		// check password strength
		if(InputValidator.validatePassword(password)){
			validChecker++;
		}else{
			setInvalidInputMessage("Invalid Input - The Password must contain:" +
					"\nAt least 8 characters" +
					"\nAt least 1 uppercase letter" +
					"\nAt least 1 lowercase letter" +
					"\nAt least 1 number OR special character");
		}

		// check password and password verify match (case sensitive) 
		if(password.equals(passwordVerify)){
			validChecker++;
		}else{
			setInvalidInputMessage("Invalid Input - The passwords do not match");
		}

		// check email validity
		if(InputValidator.validateEmail(email)){
			validChecker++;
		}else{
			setInvalidInputMessage("Invalid Input - The E-mail is not valid");
		}

		// check username is not empty or whitespace
		if(InputValidator.validateUsername(username)){
			validChecker++;
		}else{
			setInvalidInputMessage("Invalid Input - The Username can contain only letters, numbers and underscores");
		}
		
		//first and last name aren't required so they can be empty (just not null)
		if(first == null){
			first = ""; //make it empty string
		}
		
		if(last == null){
			last = ""; //make it empty string
		}
		
		//check if all checks passed [4 checks total]
		if(validChecker == numToMeetChecks){
			allIsValid = true;
		}

		return allIsValid;
	}

	/**
	 * @return the invalidInputMessage
	 */
	public String getInvalidInputMessage() {
		return invalidInputMessage;
	}

	/**
	 * @param invalidInputMessage the invalidInputMessage to set
	 */
	public void setInvalidInputMessage(String invalidInputMessage) {
		this.invalidInputMessage = invalidInputMessage;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the passwordVerify
	 */
	public String getPasswordVerify() {
		return passwordVerify;
	}

	/**
	 * @param passwordVerify the passwordVerify to set
	 */
	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * For debuggin purposes, pre-fill data with valid data [to speed up testing time]
	 * @param enable
	 */
	private String[] debugValues(boolean enabled){
		if(enabled){
			String[] values = {"Test_Name1", "testPassw0rd*", "testPassw0rd*", "foo", "bar", "testMail@test.edu"};
			return values;
		}else{
			//debugging disabled. 
			return null;
		}
	}
	
	/**
	 * Handle network connections in an AsyncTask
	 * @author Eileen Balci
	 */
	private class NetworkConnections extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			serviceConnection(getUsername(), getPassword(), getFirstName(), getLastName(), getEmail());
			return null;
		}
		
		/**
		 * https://github.com/spring-projects/spring-android
		 */
		private void serviceConnection(String username, String password,
				String firstName, String lastName, String email) {
			try {

				String restUrl = "http://phototrackerservice-env.elasticbeanstalk.com/rest/services/registration/register";

				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(
						new MappingJacksonHttpMessageConverter());

				Map<String, String> request = new HashMap<String, String>();
				request.put(DatabaseMapping.NAME, firstName + " " + lastName);
				request.put(DatabaseMapping.DOB, "5-7-81"); //Dummy data for now, this will be a future feature
				request.put(DatabaseMapping.USERNAME, username);
				request.put(DatabaseMapping.PASSWORD, password);
				request.put(DatabaseMapping.EMAIL, email);

				String response = restTemplate.postForObject(restUrl, request,
						String.class);

				Log.d(TAG, response);
			} catch (ArrayIndexOutOfBoundsException e) {
				Log.e(TAG,
						"Arguments Missing for Main Thread: " + e.getMessage());
			}
		}

	}
}
