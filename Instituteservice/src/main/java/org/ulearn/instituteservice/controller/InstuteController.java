package org.ulearn.instituteservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/institute")
public class InstuteController {
	
	@GetMapping("/all")
	public String getInstute() {
		return "Welcone to institute";
	}
}
