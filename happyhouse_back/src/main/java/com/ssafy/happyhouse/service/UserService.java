package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserResultDto;

public interface UserService {
	public UserResultDto userRegister(UserDto userDto);
	public UserDto userSelect(String userId);
	public UserResultDto userUpdate(UserDto userDto);
	public UserResultDto userDelete(String userId);
}
