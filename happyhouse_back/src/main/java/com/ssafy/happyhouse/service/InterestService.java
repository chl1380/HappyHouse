package com.ssafy.happyhouse.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.InterestDto;

public interface InterestService {
	public List<Integer> getInterest(int myNo) throws SQLException;
	public int intInsert(InterestDto interestDto) throws SQLException;
	public int intDelete(InterestDto interestDto) throws SQLException;
	public List<HouseInfoDto> getInterestList(int userSeq) throws Exception;
}
