package edu.vt.cs5744.phototracker.service;

import static edu.vt.cs5744.phototracker.service.Utility.DOB;
import static edu.vt.cs5744.phototracker.service.Utility.EMAIL;
import static edu.vt.cs5744.phototracker.service.Utility.NAME;
import static edu.vt.cs5744.phototracker.service.Utility.PASSWORD;
import static edu.vt.cs5744.phototracker.service.Utility.USERNAME;
import static edu.vt.cs5744.phototracker.service.Utility.USER_BUCKET;
import static edu.vt.cs5744.phototracker.service.Utility.objectToInputstream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

@Component("aWSS3Service")
public class AWSS3ServiceImpl implements IAWSS3Service {

	private static Logger logger = Logger.getLogger(AWSS3ServiceImpl.class);
	private AmazonS3 s3 ;
	private Region usWest2 ;
	

    @PostConstruct
    public void customInit()
    {
		try {
			s3 = new AmazonS3Client(
					new ClasspathPropertiesFileCredentialsProvider());
			usWest2 = Region.getRegion(Regions.US_EAST_1);
			s3.setRegion(usWest2);
			s3.createBucket(USER_BUCKET);
		} catch (AmazonServiceException ase) {
            logger.debug("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            logger.debug("Error Message:    " + ase.getMessage());
            logger.debug("HTTP Status Code: " + ase.getStatusCode());
            logger.debug("AWS Error Code:   " + ase.getErrorCode());
            logger.debug("Error Type:       " + ase.getErrorType());
            logger.debug("Request ID:       " + ase.getRequestId());
            throw ase;
        } catch (AmazonClientException ace) {
            logger.debug("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            logger.debug("Error Message: " + ace.getMessage());
            throw ace;
        }
    }
	  
	@Override
	public Bucket createBucket(String bucketName) {
		try {
			if (bucketName == null || bucketName.equals(""))
				return null;
			return s3.createBucket(bucketName);
		} catch (AmazonServiceException ase) {
            logger.debug("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            logger.debug("Error Message:    " + ase.getMessage());
            logger.debug("HTTP Status Code: " + ase.getStatusCode());
            logger.debug("AWS Error Code:   " + ase.getErrorCode());
            logger.debug("Error Type:       " + ase.getErrorType());
            logger.debug("Request ID:       " + ase.getRequestId());
            throw ase;
        } catch (AmazonClientException ace) {
            logger.debug("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            logger.debug("Error Message: " + ace.getMessage());
            throw ace;
        }
	}

	@Override
	public PutObjectResult putUserObject(String bucketName, String key, User user) {
		if (bucketName == null || bucketName.equals("")) return null;
		
		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.addUserMetadata(USERNAME, user.getUserName());
			objectMetadata.addUserMetadata(PASSWORD, user.getPassword());
			objectMetadata.addUserMetadata(NAME, user.getName());
			objectMetadata.addUserMetadata(DOB, user.getDateOfBirth());
			objectMetadata.addUserMetadata(EMAIL, user.getEmail());
			InputStream is = objectToInputstream(user);
			try {
				objectMetadata.setContentLength(is.available());
			} catch (IOException e) {
				logger.debug(e.getMessage());
			}
			return s3.putObject(new PutObjectRequest(bucketName, key, is,
					objectMetadata));
		} catch (AmazonServiceException ase) {
            logger.debug("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            logger.debug("Error Message:    " + ase.getMessage());
            logger.debug("HTTP Status Code: " + ase.getStatusCode());
            logger.debug("AWS Error Code:   " + ase.getErrorCode());
            logger.debug("Error Type:       " + ase.getErrorType());
            logger.debug("Request ID:       " + ase.getRequestId());
//            throw ase;
        } catch (AmazonClientException ace) {
            logger.debug("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            logger.debug("Error Message: " + ace.getMessage());
//            throw ace;
        }
		return null;
	}

	@Override
	public User getUserObject(String bucketName, String key) {
		try {
			if (bucketName == null || bucketName.equals(""))
				return null;
			S3Object s3Object =  s3.getObject(new GetObjectRequest(bucketName, key));
			if(s3Object != null){
				Map<String,String> userData = s3Object.getObjectMetadata().getUserMetadata();
				User user = new User(userData.get(NAME.toLowerCase()), userData.get(DOB.toLowerCase()), userData.get(USERNAME.toLowerCase()), userData.get(PASSWORD.toLowerCase()), userData.get(EMAIL.toLowerCase()));
				return user;
			}
		} catch (AmazonServiceException ase) {
            logger.debug("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            logger.debug("Error Message:    " + ase.getMessage());
            logger.debug("HTTP Status Code: " + ase.getStatusCode());
            logger.debug("AWS Error Code:   " + ase.getErrorCode());
            logger.debug("Error Type:       " + ase.getErrorType());
            logger.debug("Request ID:       " + ase.getRequestId());
//            throw ase;
        } catch (AmazonClientException ace) {
            logger.debug("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            logger.debug("Error Message: " + ace.getMessage());
//            throw ace;
        }
		return null;
	}
	
}
