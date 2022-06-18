package com.ssafy.happyhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.UserDao;
import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserResultDto;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	@Override
	public UserResultDto userRegister(UserDto userDto) {
		
		UserResultDto userResultDto = new UserResultDto();
		if (userDao.userRegister(userDto) == SUCCESS) { // 성공
			userResultDto.setResult(SUCCESS);
		}else { // 실패
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}
	@Override
	public UserDto userSelect(String userId) {
		
		UserDto dto = null;
		dto = userDao.userSelect(userId);

		return dto;
	}
	@Override
	public UserResultDto userUpdate(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
		if (userDao.userUpdate(userDto) == SUCCESS) { // 성공
			userResultDto.setResult(SUCCESS);
		}else { // 실패
			userResultDto.setResult(FAIL);
		}
		return userResultDto;
	}
	@Override
	public UserResultDto userDelete(String userId) {
		UserResultDto userResultDto = new UserResultDto();
		if (userDao.userDelete(userId) != FAIL) { // 성공
			userResultDto.setResult(SUCCESS);
		}else { // 실패
			userResultDto.setResult(FAIL);
		}
		return userResultDto;
	}


}
