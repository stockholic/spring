package com.taxholic.web.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taxholic.core.web.dao.StockDao;
import com.taxholic.web.test.dto.EncryptDto;


@Service
public class TestService{
	
	@Autowired
	private StockDao stockDao;
	
	
	public String getId(){
		return stockDao.getString("selectId");
	}
	
	public String getId(String id){
		return id;
	}
	
	@Transactional
	public void insertUserEncrypt(EncryptDto dto){
		
		stockDao.insert("insertMember",dto);

		stockDao.insert("insertMemberRole",dto);

	}
	
//	public EncryptTest select(){
//		return (EncryptTest) this.dao.getObject("front.board.getRoomPassword");
//	}
//	
//	public String noCashe(){
//		return this.dao.getString("front.sqlite.getTime");
//		
//	}
//	
//	@Cacheable(value="getTime")
//	public String cashe(){
//		return this.dao.getString("front.sqlite.getTime");
//	}
	
}
