package com.ssafy.happyhouse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.happyhouse.dto.NoticeDto;
import com.ssafy.happyhouse.dto.NoticeParamDto;
import com.ssafy.happyhouse.dto.NoticeResultDto;
import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.service.NoticeService;

@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true", allowedHeaders = "*", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD,
		RequestMethod.OPTIONS })
@RestController
public class NoticeController {

	@Autowired
	NoticeService service;

	public static final int SUCCESS = 1;

	@GetMapping(value = "/notices")
	public ResponseEntity<NoticeResultDto> noticeList(NoticeParamDto noticeParamDto) {

		NoticeResultDto noticeResultDto;
		if (noticeParamDto.getSearchWord().isEmpty()) {
			noticeResultDto = service.noticeList(noticeParamDto);
		} else {
			noticeResultDto = service.noticeListSearchWord(noticeParamDto);
		}
//		System.out.println(noticeResultDto);
		if (noticeResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/notices/{noticeId}")
	public ResponseEntity<NoticeResultDto> noticeDetail(@PathVariable int noticeId, HttpSession session) {

		NoticeParamDto noticeParamDto = new NoticeParamDto();
		noticeParamDto.setNoticeId(noticeId);

		UserDto userDto = (UserDto) session.getAttribute("userDto");
		System.out.println("현재 유저 정보:" + userDto);
		if (userDto != null)
			noticeParamDto.setUserSeq(userDto.getUserSeq());
		else
			noticeParamDto.setUserSeq(0);

		NoticeResultDto noticeResultDto = service.noticeDetail(noticeParamDto);
		System.out.println("보드 상세 정보: " + noticeResultDto);

		if (noticeResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/notices")
	public ResponseEntity<NoticeResultDto> noticeInsert(NoticeDto noticeDto, MultipartHttpServletRequest request) {

		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");

		noticeDto.setUserSeq(userDto.getUserSeq());

		NoticeResultDto noticeResultDto = service.noticeInsert(noticeDto, request);

		if (noticeResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/notices/{noticeId}")
	public ResponseEntity<NoticeResultDto> noticeUpdate(NoticeDto noticeDto, MultipartHttpServletRequest request) {

		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		System.out.println("로그인 정보: " + userDto);

		noticeDto.setUserSeq(userDto.getUserSeq());

		NoticeResultDto noticeResultDto  = service.noticeUpdate(noticeDto, request);
		System.out.println(noticeDto);
		System.out.println("공지사항 업데이트 결과"+noticeResultDto);
		if (noticeResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/notices/{noticeId}")
	public ResponseEntity<NoticeResultDto> noticeDelete(@PathVariable(value = "noticeId") int noticeId) {
		NoticeResultDto noticeResultDto = service.noticeDelete(noticeId);
		
		if (noticeResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
