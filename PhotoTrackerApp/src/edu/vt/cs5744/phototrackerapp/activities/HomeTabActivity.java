package edu.vt.cs5744.phototrackerapp.activities;

import edu.vt.cs5744.phototrackerapp.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * From this activity the user will be able to select a few options such as
 * View Photos
 * View Map
 * View Friends [future feature]
 * 
 * However Friends will not be implemented for the first version of the applicaiton prototype
 * 
 * @author Eileen Balci
 *
 */
public class HomeTabActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homescreen);
		
		
	}

}
