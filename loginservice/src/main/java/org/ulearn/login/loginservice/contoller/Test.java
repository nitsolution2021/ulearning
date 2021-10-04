package org.ulearn.login.loginservice.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
	
	@GetMapping("/test")
	public String test() {
		return "Project is running"; 
	}

}
