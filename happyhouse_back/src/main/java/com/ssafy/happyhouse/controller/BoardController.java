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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.happyhouse.dto.BoardDto;
import com.ssafy.happyhouse.dto.BoardParamDto;
import com.ssafy.happyhouse.dto.BoardResultDto;
import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.service.BoardService;

@RestController
@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, 
					RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS })
public class BoardController {

	@Autowired
	BoardService service;
	
	private static final int SUCCESS = 1;

    @GetMapping(value="/boards")
    public ResponseEntity<BoardResultDto> boardList(BoardParamDto boardParamDto){

        BoardResultDto boardResultDto;

	    if( boardParamDto.getSearchWord().isEmpty() ) {
	        boardResultDto = service.boardList(boardParamDto);
	    }else {
	        boardResultDto = service.boardListSearchWord(boardParamDto);
	    }

	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
    
    @GetMapping(value="/boardsad")
    public ResponseEntity<BoardResultDto> boardListad(BoardParamDto boardParamDto){

        BoardResultDto boardResultDto;

        boardResultDto = service.boardListad(boardParamDto);

	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@GetMapping(value="/boards/{boardId}")
	public ResponseEntity<BoardResultDto> boardDetail(@PathVariable int boardId, HttpSession session){
	
	    BoardParamDto boardParamDto = new BoardParamDto();
	    boardParamDto.setBoardId(boardId);
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    if(userDto != null) boardParamDto.setUserSeq( userDto.getUserSeq());
	    else boardParamDto.setUserSeq(0);
	    
	    BoardResultDto boardResultDto = service.boardDetail(boardParamDto);
	
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}

	@PostMapping(value="/boards")
	public ResponseEntity<BoardResultDto> boardInsert(
			BoardDto boardDto, 
	        MultipartHttpServletRequest request) {
	    

	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    boardDto.setUserSeq(userDto.getUserSeq());
	            
	    BoardResultDto boardResultDto = service.boardInsert(boardDto, request);
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}

	@PostMapping(value="/boards/{boardId}") 
	public ResponseEntity<BoardResultDto> boardUpdate(
			BoardDto boardDto, 
	        MultipartHttpServletRequest request){
	
	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    System.out.println(userDto);
	    
	    boardDto.setUserSeq(userDto.getUserSeq());
	    
	    BoardResultDto boardResultDto = service.boardUpdate(boardDto, request);
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }        
	}

	@DeleteMapping(value="/boards/{boardId}") 
	public ResponseEntity<BoardResultDto> boardDelete(@PathVariable(value="boardId") int boardId){
	    BoardResultDto boardResultDto = service.boardDelete(boardId);
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
}
