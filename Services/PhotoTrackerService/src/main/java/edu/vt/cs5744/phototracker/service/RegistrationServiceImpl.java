package edu.vt.cs5744.phototracker.service;

import static edu.vt.cs5744.phototracker.service.Utility.DOB;
import static edu.vt.cs5744.phototracker.service.Utility.EMAIL;
import static edu.vt.cs5744.phototracker.service.Utility.NAME;
import static edu.vt.cs5744.phototracker.service.Utility.PASSWORD;
import static edu.vt.cs5744.phototracker.service.Utility.USERNAME;
import static edu.vt.cs5744.phototracker.service.Utility.USER_BUCKET;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationServiceImpl implements IRegistrationService {
	
	private static Logger logger = Logger.getLogger(AWSS3ServiceImpl.class);

	@Autowired
	public IAWSS3Service aWSS3Service;

	@Override
	public String registerUser(Map<String, String> userdata) {

		String returnString = "USER ALREADY EXIST";

		try {
			String name = (String) userdata.get(NAME);
			String dateOfBirth = (String) userdata.get(DOB);
			String userName = (String) userdata.get(USERNAME);
			String password = (String) userdata.get(PASSWORD);
			String email = (String) userdata.get(EMAIL);
			User user = aWSS3Service.getUserObject(USER_BUCKET, userName);
			if (user == null) {

				user = new User(name, dateOfBirth, userName, password,
						email);
				aWSS3Service.putUserObject(USER_BUCKET, userName, user);
				returnString = "REGISTERED";
			}
		} catch (Exception e) {
			logger.debug("Exception While Registration" + e);
			returnString = "Exception While Registration";
		}
		return returnString;
	
	}

}
