package kr.pethub.webapp.admin.batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pethub.webapp.admin.batch.dao.SiteInfoDao;
import kr.pethub.webapp.admin.batch.model.SiteInfo;

@Service
public class SiteInfoService {

	@Autowired
	private SiteInfoDao siteInfoDao;

	/**
	 * 목록 수	
	 * @param siteInfo
	 * @return
	 */
	public int selectSiteInfoCount(SiteInfo siteInfo) {
		return siteInfoDao.selectSiteInfoCount(siteInfo);
	}

	/**
	 * 목록
	 * @param siteInfo
	 * @return
	 */
	public List<SiteInfo> selectSiteInfoList(SiteInfo siteInfo) {
		
		siteInfo.setTotalRow(selectSiteInfoCount(siteInfo));
		List<SiteInfo> list =  siteInfoDao.selectSiteInfoList(siteInfo);
		
		int num = siteInfo.getTotalRow() - siteInfo.getRowStart();
		
		for (SiteInfo si : list) {
			si.setNum(num);
			num--;
		}
		
		return list;
	}
	
	/**
	 * 조회
	 * @param siteInfo
	 * @return
	 */
	public SiteInfo selectSiteInfo(String siteSrl) {
		return  siteInfoDao.selectSiteInfo(siteSrl);
	}

}
