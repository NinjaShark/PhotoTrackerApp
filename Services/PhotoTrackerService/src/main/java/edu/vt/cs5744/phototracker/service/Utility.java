package edu.vt.cs5744.phototracker.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

public class Utility {
	
	public static final String NAME = "NAME"; 
	public static final String DOB = "DOB"; 
	public static final String USERNAME = "USERNAME"; 
	public static final String PASSWORD = "PASSWORD"; 
	public static final String EMAIL = "EMAIL"; 
	
	public static final String USER_BUCKET = "phtoto-tracker-user";


	public static InputStream objectToInputstream(Object obj){

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

    public static File objectToFile(Object obj) {
        File file = null;
		try {
		
			file = File.createTempFile("aws-java-sdk-", ".txt");
			file.deleteOnExit();
			
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(obj);
			oos.flush();
			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return file;
    }
    
    public static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }


}
