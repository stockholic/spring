package kr.pethub.webapp.admin.batch.model;

import kr.pethub.core.module.model.Pagination;

public class SiteInfo extends Pagination{
	
	private String siteSrl;					// 사이트 일련번호
	private String siteNm;				// 사이트명 
	
	public String getSiteSrl() {
		return siteSrl;
	}
	public void setSiteSrl(String siteSrl) {
		this.siteSrl = siteSrl;
	}
	public String getSiteNm() {
		return siteNm;
	}
	public void setSiteNm(String siteNm) {
		this.siteNm = siteNm;
	}
	
} 
