package edu.vt.cs5744.phototracker.service;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;


@Service("registration")
@Path("services/registration")
public interface IRegistrationService {

	@POST
	@Produces("text/plain")
	@Consumes("application/json") 
	@Path("/register")
	public String registerUser(Map<String, String> userdata );

}
