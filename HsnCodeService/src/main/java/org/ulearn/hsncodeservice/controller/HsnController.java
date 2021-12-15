package org.ulearn.hsncodeservice.controller;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.hsncodeservice.entity.GlobalResponseEntity;
import org.ulearn.hsncodeservice.entity.HsnCodeEntity;
import org.ulearn.hsncodeservice.exception.CustomException;
import org.ulearn.hsncodeservice.services.HsnServices;

@RestController
@RequestMapping("/hsn")
public class HsnController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HsnController.class);
	
	@Autowired
	private HsnServices hsnServices;
	
	@PostMapping("/add")
	public GlobalResponseEntity addHsnCode(@RequestBody HsnCodeEntity hsnCodeEntity) {
		LOGGER.info("Inside - HsnController.addHsnCode()");
		try {
			return hsnServices.addHsnCode(hsnCodeEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping(value = "/list/{page}/{limit}/{sortName}/{sort}/{delete}")
	public Map<String, Object> getHsnCode(@PathVariable("delete") int isDelete, @PathVariable("page") int page, @PathVariable("limit") int limit,
			@PathVariable("sort") String sort, @PathVariable("sortName") String sortName,
			@RequestParam(defaultValue = "") Optional<String> keyword, @RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - HsnController.getHsnCode()");
		try {
			return hsnServices.getHsnCode(page, limit, sort, sortName, keyword, sortBy, isDelete);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
}
