package com.ssafy.happyhouse.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.InterestDto;

@Mapper
public interface InterestDao {
	public List<Integer> getInterest(int myNo);
	public int intInsert(InterestDto interestDto);
	public int intDelete(InterestDto interestDto);
	public List<HouseInfoDto> getInterestList(int userSeq);
}
