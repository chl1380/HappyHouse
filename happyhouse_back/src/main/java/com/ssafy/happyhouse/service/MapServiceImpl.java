package com.ssafy.happyhouse.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.MapDao;
import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.SidoGugunCodeDto;

@Service
public class MapServiceImpl implements MapService {
	
	@Autowired
	MapDao dao;
	
	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		System.out.println(dao.getSido());
		return dao.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return dao.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return dao.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		System.out.println("서비스");
		System.out.println(dao.getAptInDong(dong));
		return dao.getAptInDong(dong);
	}

	@Override
	public List<HouseInfoDto> getApt() throws SQLException {
		return dao.getApt();
	}
}
