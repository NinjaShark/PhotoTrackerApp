package edu.vt.cs5744.phototrackerapp.activities;

import android.app.Activity;
import android.os.Bundle;
import edu.vt.cs5744.phototrackerapp.R;

/**
 * This layout holds the users home tabs which include the homescreen,
 * camera and account tabs
 * 
 * @author Eileen Balci
 *
 */
public class TabLayoutActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabs);
	}

}
