package edu.vt.cs5744.phototracker.service;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;


@Service("login")
@Path("services/login")
public interface ILoginService {

	@POST
	@Produces("text/plain")
	@Consumes("application/json") 
	@Path("/login")
	public String loginUser(Map<String, String> userdata );

}
