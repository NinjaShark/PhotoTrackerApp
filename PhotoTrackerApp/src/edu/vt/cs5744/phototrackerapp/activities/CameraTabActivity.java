package edu.vt.cs5744.phototrackerapp.activities;

import android.app.Activity;
import android.os.Bundle;
import edu.vt.cs5744.phototrackerapp.R;

/**
 * This class will handle the functionality for the Camera View tab
 * 
 * From here the user shall be able to access the Camera and take a picture
 * 
 * @author Eileen Balci
 *
 */
public class CameraTabActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
	}

}
