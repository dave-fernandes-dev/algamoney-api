package dev.fernandes.dave.algamoney.api.resource;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class AppVersionResource {
	
	@GetMapping()
	public String version() {
		return "2022-06-20 21:54";
	}
	
}