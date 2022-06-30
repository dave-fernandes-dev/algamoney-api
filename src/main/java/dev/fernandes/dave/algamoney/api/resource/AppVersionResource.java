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
		return "2022-06-28 12:54:00";
	}
	
	@GetMapping("/profiles")
    public String getActiveProfiles() {
		
		System.out.println(environment.toString());
		
		StringBuilder profiles = new StringBuilder();
		
		int i = 1;
        for (String profileName : environment.getActiveProfiles()) {
            profiles.append("\n ["+ i++ +"] Currently active profile >> " + profileName.toString());
            System.out.println("Active profiles: " + Arrays.toString(environment.getActiveProfiles()));
        }
		return profiles.toString();  
    }
	
	@GetMapping("/env")
    public String getEnvs() {
		
		System.out.println(environment.toString());		
		
		return environment.toString();  
    }
	
}