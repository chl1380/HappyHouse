package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.SidoGugunCodeDto;
import com.ssafy.happyhouse.service.MapService;

@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, 
					RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/map")
public class MapController {

	@Autowired
	MapService service;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>>  sido() throws Exception{
		if(service.getSido() != null) {
			return new ResponseEntity<List<SidoGugunCodeDto>>(service.getSido(), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<SidoGugunCodeDto>>(service.getSido(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/gugun/{sido}")
	public ResponseEntity<List<SidoGugunCodeDto>>  gugun(@PathVariable String sido) throws Exception{
		if(service.getGugunInSido(sido) != null) {
			return new ResponseEntity<List<SidoGugunCodeDto>>(service.getGugunInSido(sido), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<SidoGugunCodeDto>>(service.getGugunInSido(sido), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/dong/{gugun}")
	public ResponseEntity<List<HouseInfoDto>>  dong(@PathVariable String gugun) throws Exception{
		if(service.getDongInGugun(gugun) != null) {
			return new ResponseEntity<List<HouseInfoDto>>(service.getDongInGugun(gugun), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<HouseInfoDto>>(service.getDongInGugun(gugun), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/apt/{dong}")
	public ResponseEntity<List<HouseInfoDto>>  apt(@PathVariable String dong) throws Exception{
		List<HouseInfoDto> list = service.getAptInDong(dong);
		if(list != null) {
			return new ResponseEntity<List<HouseInfoDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<HouseInfoDto>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
