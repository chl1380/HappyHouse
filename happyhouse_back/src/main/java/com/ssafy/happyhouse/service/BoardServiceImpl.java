package com.ssafy.happyhouse.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.happyhouse.dao.BoardDao;
import com.ssafy.happyhouse.dto.BoardDto;
import com.ssafy.happyhouse.dto.BoardFileDto;
import com.ssafy.happyhouse.dto.BoardParamDto;
import com.ssafy.happyhouse.dto.BoardResultDto;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDao dao;
	
	private static final String uploadFolder = "upload";
	
	// 파일이 저장될 경로를 하드코딩
    /* for eclipse development code */
    private static final String uploadPath = "C:" + File.separator + "boot_workspace"
            + File.separator + "happyhouse" 
            + File.separator + "src" 
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "static";
    
    private static final int SUCCESS = 1;
    private static final int FAIL = -1;
    
	@Override
	public BoardResultDto boardDetail(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
	    
	    try {
	    	if(boardParamDto.getUserSeq() != 0) {
		        int userReadCnt = dao.boardUserReadCount(boardParamDto);
		        if( userReadCnt == 0 ) {
		            dao.boardUserReadInsert(boardParamDto.getBoardId(), boardParamDto.getUserSeq());
		            dao.boardReadCountUpdate(boardParamDto.getBoardId());
		        }
	    	}
		        
		        BoardDto boardDto = dao.boardDetail(boardParamDto);
		        if( boardDto.getUserSeq() == boardParamDto.getUserSeq() || boardParamDto.getUserSeq() == 1 ) {
		            boardDto.setSameUser(true);
		        } else {
		            boardDto.setSameUser(false);
		        }
		        List<BoardFileDto> fileList = dao.boardDetailFileList(boardDto.getBoardId());
		
		        boardDto.setFileList(fileList);
		        boardResultDto.setDto(boardDto);
		
		        boardResultDto.setResult(SUCCESS);
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}

	@Override
	@Transactional
	public BoardResultDto boardDelete(int boardId) {
		BoardResultDto boardResultDto = new BoardResultDto();
	    
	    try {
	        List<String> fileUrlList = dao.boardFileUrlDeleteList(boardId);
	        for(String fileUrl : fileUrlList) {
	            File file = new File(uploadPath + File.separator, fileUrl);                
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	        
	        dao.boardFileDelete(boardId);
	        dao.boardReadCountDelete(boardId);
	        dao.boardDelete(boardId); // 마지막으로   
	        boardResultDto.setResult(SUCCESS);
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}

	@Override
	@Transactional
	public BoardResultDto boardUpdate(BoardDto dto, MultipartHttpServletRequest request) {
		BoardResultDto boardResultDto = new BoardResultDto();
	    
	    try {
	        dao.boardUpdate(dto);
	
	        List<MultipartFile> fileList = request.getFiles("file");
	        
	        File uploadDir = new File(uploadPath + File.separator + uploadFolder);
	        if (!uploadDir.exists()) uploadDir.mkdir();
	        
	        // 물리 파일 삭제, 첨부파일 여러개 고려
	        List<String> fileUrlList = dao.boardFileUrlDeleteList(dto.getBoardId());    
	        for(String fileUrl : fileUrlList) {    
	            File file = new File(uploadPath + File.separator, fileUrl);
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	
	        dao.boardFileDelete(dto.getBoardId()); // 테이블 파일 삭제
	                
	        for (MultipartFile part : fileList) {
	            int boardId = dto.getBoardId();
	            
	            String fileName = part.getOriginalFilename();
	            
	            //Random File Id
	            UUID uuid = UUID.randomUUID();
	            
	            //file extension
	            String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()
	        
	            String savingFileName = uuid + "." + extension;
	        
	            File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            
	            System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            part.transferTo(destFile);
	        
	            // Table Insert
	            BoardFileDto boardFileDto = new BoardFileDto();
	            boardFileDto.setBoardId(boardId);
	            boardFileDto.setFileName(fileName);
	            boardFileDto.setFileSize(part.getSize());
	            boardFileDto.setFileContentType(part.getContentType());
	            String boardFileUrl = uploadFolder + "/" + savingFileName;
	            boardFileDto.setFileUrl(boardFileUrl);
	            
	            dao.boardFileInsert(boardFileDto);
	        }
	
	        boardResultDto.setResult(SUCCESS);
	        
	    } catch(IOException e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}

	@Override
	@Transactional
	public BoardResultDto boardInsert(BoardDto dto, MultipartHttpServletRequest request) {
		BoardResultDto boardResultDto = new BoardResultDto();

        try {
            dao.boardInsert(dto); // useGeneratedKeys="true" keyProperty="boardId"

            List<MultipartFile> fileList = request.getFiles("file");

            File uploadDir = new File(uploadPath + File.separator + uploadFolder);
            if (!uploadDir.exists()) uploadDir.mkdir();

	        for (MultipartFile part : fileList) {
	
	            int boardId = dto.getBoardId();
	            
	            String fileName = part.getOriginalFilename();
	            
	            //Random File Id
	            UUID uuid = UUID.randomUUID();
	            
	            //file extension (확장자)
	            String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()
	        
	            String savingFileName = uuid + "." + extension;
	        
	            File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            
	            System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            part.transferTo(destFile);
	        
	            // Table Insert
	            BoardFileDto boardFileDto = new BoardFileDto();
	            boardFileDto.setBoardId(boardId);
	            boardFileDto.setFileName(fileName);
	            boardFileDto.setFileSize(part.getSize());
	            boardFileDto.setFileContentType(part.getContentType());
	            String boardFileUrl = uploadFolder + "/" + savingFileName;
	            boardFileDto.setFileUrl(boardFileUrl);
	            
	            dao.boardFileInsert(boardFileDto);
	        }
	
	        boardResultDto.setResult(SUCCESS);
	        
	    } catch(IOException e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    return boardResultDto;
	}

	@Override
	public BoardResultDto boardList(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
	    
	    try {
	        List<BoardDto> list = dao.boardList(boardParamDto);
	        int count = dao.boardListTotalCount();            
	        boardResultDto.setList(list);
	        boardResultDto.setCount(count);
	        boardResultDto.setResult(SUCCESS);
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}
	
	@Override
	public BoardResultDto boardListad(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
	    
	    try {
	        List<BoardDto> list = dao.boardListad(boardParamDto);
	        int count = dao.boardListAdminTotalCount();            
	        boardResultDto.setList(list);
	        boardResultDto.setCount(count);
	        boardResultDto.setResult(SUCCESS);
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}

	@Override
	public BoardResultDto boardListSearchWord(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
	    
	    try {
	        List<BoardDto> list = dao.boardListSearchWord(boardParamDto);
	        int count = dao.boardListSearchWordTotalCount(boardParamDto);
	        
	        boardResultDto.setList(list);
	        boardResultDto.setCount(count);
	        
	        boardResultDto.setResult(SUCCESS);
	    
	    } catch(Exception e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}

}
