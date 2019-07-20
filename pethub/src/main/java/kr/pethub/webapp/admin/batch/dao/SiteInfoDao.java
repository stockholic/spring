package kr.pethub.webapp.admin.batch.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import kr.pethub.core.module.dao.MultiSqlSessionDaoSupport;
import kr.pethub.webapp.admin.batch.model.SiteInfo;


@Repository
public class SiteInfoDao extends MultiSqlSessionDaoSupport{	
	
	public int selectSiteInfoCount(SiteInfo siteInfo){
		return  getInt("selectSiteInfoCount",siteInfo);
	}

	public List<SiteInfo> selectSiteInfoList(SiteInfo siteInfo){
		return  selectList("selectSiteInfoList",siteInfo);
	}

}
