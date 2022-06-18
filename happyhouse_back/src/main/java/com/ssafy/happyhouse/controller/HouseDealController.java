package com.ssafy.happyhouse.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.HouseDealDto;
import com.ssafy.happyhouse.service.HouseDealService;

@RestController
@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, 
					RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS })
public class HouseDealController {
	@Autowired
	HouseDealService service;
	
	@GetMapping(value="/housedeal/{houseNo}")
	public ResponseEntity<List<HouseDealDto>> houseDealList(@PathVariable int houseNo) throws Exception {
		System.out.println("service");
		System.out.println(service.houseDealList(houseNo));
		return new ResponseEntity<List<HouseDealDto>>(service.houseDealList(houseNo), HttpStatus.OK);
	}
}
