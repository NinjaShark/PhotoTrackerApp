package edu.vt.cs5744.phototracker.service;

import static edu.vt.cs5744.phototracker.service.Utility.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class LoginClient {

	public static void main(String[] args) {
		try {

			String restUrl = "http://phototrackerservice-env.elasticbeanstalk.com/rest/services/login/login";

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			
			Map<String, String> request = new HashMap<String, String>();
			request.put(USERNAME, "Prixpress");
			request.put(PASSWORD, "password");

			String response = restTemplate
					.postForObject(restUrl, request, String.class);

			System.out.println(response);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("Arguments Missing for Main Thread: "
						+ e.getMessage());
				System.exit(-1);
		}
	}

}
