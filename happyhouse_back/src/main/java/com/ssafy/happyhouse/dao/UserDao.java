package com.ssafy.happyhouse.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserResultDto;

@Mapper
public interface UserDao {
	public int userRegister(UserDto userDto);
	public UserDto userSelect(String userId);
	public int userUpdate(UserDto userDto);
	public int userDelete(String userId);
}
