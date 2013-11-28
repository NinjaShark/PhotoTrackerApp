package edu.vt.cs5744.phototracker.service;

import static edu.vt.cs5744.phototracker.service.Utility.PASSWORD;
import static edu.vt.cs5744.phototracker.service.Utility.USERNAME;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static edu.vt.cs5744.phototracker.service.Utility.*;

public class LoginServiceImpl implements ILoginService {
	
	private static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	@Autowired
	public IAWSS3Service aWSS3Service;

	@Override
	public String loginUser(Map<String, String> userdata) {

		if(userdata == null || userdata.isEmpty()) return "NO USER INFO";

		String returnString = null;
		try {
			returnString = "USER ALREADY EXIST";
			String userName = (String) userdata.get(USERNAME);
			String password = (String) userdata.get(PASSWORD);
			if (userName == null || userName.isEmpty() || password == null
					|| password.isEmpty())
				return "NO USER INFO";
			User user = aWSS3Service.getUserObject(USER_BUCKET, userName);
			if (user == null) {
				returnString = "USER IS NOT REGISTERED";
			} else {
				String existingPassword = user.getPassword();
				if (password.equals(existingPassword)) {
					returnString = "AUTHENTICATED";
				} else {
					returnString = "UNAUTHORIZED";
				}
			}
		} catch (Exception e) {
			logger.debug("Error During Login", e);
		}
		return returnString;
	
	}

}
