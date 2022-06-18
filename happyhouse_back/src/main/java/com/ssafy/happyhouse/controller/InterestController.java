package com.ssafy.happyhouse.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.InterestDto;
import com.ssafy.happyhouse.service.InterestService;

@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, 
					RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS })
@RestController
public class InterestController {

	@Autowired
	InterestService service;
	
	@GetMapping(value="/interests/{userSeq}")
	public ResponseEntity<List<Integer>> intList(@PathVariable int userSeq, HttpSession session) throws Exception {
		if(service.getInterest(userSeq) != null) {
			return new ResponseEntity<List<Integer>>(service.getInterest(userSeq), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Integer>>(service.getInterest(userSeq), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/interestslist/{userSeq}")
	public ResponseEntity<List<HouseInfoDto>> getIntList(@PathVariable int userSeq, HttpSession session) throws Exception {
		System.out.println(userSeq);
		if(service.getInterestList(userSeq) != null) {
			return new ResponseEntity<List<HouseInfoDto>>(service.getInterestList(userSeq), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<HouseInfoDto>>(service.getInterestList(userSeq), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value="/interests")
	public ResponseEntity<Integer> intInsert(@RequestBody InterestDto interestDto) throws Exception {
		if(service.intInsert(interestDto) == 1) {
			return new ResponseEntity<Integer>(service.intInsert(interestDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(service.intInsert(interestDto), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="/interests")
	public ResponseEntity<Integer> intDelete(@RequestBody InterestDto interestDto) throws Exception {
		System.out.println(interestDto.getUserSeq());
		System.out.println(interestDto.getHouseNo());
		if(service.intDelete(interestDto) == 1) {
			return new ResponseEntity<Integer>(service.intDelete(interestDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(service.intDelete(interestDto), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
