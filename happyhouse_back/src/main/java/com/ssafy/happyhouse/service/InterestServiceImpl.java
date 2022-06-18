package com.ssafy.happyhouse.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.InterestDao;
import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.InterestDto;

@Service
public class InterestServiceImpl implements InterestService{

	@Autowired
	InterestDao dao;
	
	@Override
	public List<Integer> getInterest(int myNo) throws SQLException {
		return dao.getInterest(myNo);
	}

	@Override
	public int intInsert(InterestDto interestDto) throws SQLException {
		return dao.intInsert(interestDto);
	}

	@Override
	public int intDelete(InterestDto interestDto) throws SQLException {
		return dao.intDelete(interestDto);
	}

	@Override
	public List<HouseInfoDto> getInterestList(int userSeq) throws Exception {
		return dao.getInterestList(userSeq);
	}

}
