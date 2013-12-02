package edu.vt.cs5744.phototrackerapp.activities;

import edu.vt.cs5744.phototrackerapp.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * This activity will handle the functionality for changing user account information.
 * 
 * From here the user will be able to change their name, username, and password.
 * The user will not be able to change their email as that will be the primary key in the database and must
 * remain unique and unchanged. The user would have to make a new account and delete this account if they wanted
 * to change the email.
 * 
 * Currently for version 1 there will be no support for deleting an account.
 * 
 * @author Eileen Balci
 *
 */
public class AccountTabActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		
	}
}
