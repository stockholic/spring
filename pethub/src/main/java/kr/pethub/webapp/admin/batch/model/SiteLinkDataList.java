package kr.pethub.webapp.admin.batch.model;

import java.util.List;
import kr.pethub.core.module.model.SiteLinkData;
import kr.pethub.core.module.model.Pagination;

public class SiteLinkDataList extends Pagination{
	
	
	private List<SiteLinkData> dataList;

	public List<SiteLinkData> getDataList() {
		return dataList;
	}

	public void setDataList(List<SiteLinkData> dataList) {
		this.dataList = dataList;
	}

	
} 
