package edu.vt.cs5744.phototracker.service;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;

public interface IAWSS3Service {

	public Bucket createBucket(String bucketName);
	
	public PutObjectResult putUserObject(String bucketName, String key, User obj);
	
	public User getUserObject(String bucketName, String key);
	
}
