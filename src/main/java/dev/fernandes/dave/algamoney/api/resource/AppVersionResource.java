package dev.fernandes.dave.algamoney.api.resource;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppVersionResource {

	@Autowired
	private Environment environment;
	
	@GetMapping("/version")
	public String version() {
		return "2022-06-30 15:58:00";
	}
	
	@GetMapping("/profiles")
    public String getActiveProfiles() {
		return "Active profiles: " + Arrays.toString(environment.getActiveProfiles());  
    }
	
	@GetMapping("/env")
    public String getEnvs() {		
		return environment.toString();  
    }
	
}