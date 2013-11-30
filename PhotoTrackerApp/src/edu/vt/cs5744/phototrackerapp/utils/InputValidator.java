package edu.vt.cs5744.phototrackerapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds methods used for validating user inputs
 * 
 * @author EileenBalci
 *
 */
public class InputValidator {
	
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
	public static boolean validateEmail(String email){
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
	public static boolean validatePassword(String password){
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

}
