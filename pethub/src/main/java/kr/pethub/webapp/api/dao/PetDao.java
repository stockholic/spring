package kr.pethub.webapp.api.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import kr.pethub.core.module.dao.MultiSqlSessionDaoSupport;
import kr.pethub.webapp.api.model.SiteLinkData;


@Repository
public class PetDao extends MultiSqlSessionDaoSupport{	
	
	public int selectPetCount(String searchString){
		return  getInt("selectPetCount",searchString);
	}

	public List<SiteLinkData> selectPetList(String searchString){
		return  selectList("selectPetList",searchString);
	}

}
