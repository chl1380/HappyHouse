package com.ssafy.happyhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.LoginDao;
import com.ssafy.happyhouse.dto.UserDto;



@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;
	
	@Override
	public UserDto login(UserDto dto) { // 로그인 처리
			
		UserDto userDto = loginDao.login(dto.getUserId());
		System.out.println("loginService: " + userDto);
		if(userDto != null && userDto.getUserPassword().equals(dto.getUserPassword())) {
			return userDto;
		}else {
			return null;
		}
		
	}

}
