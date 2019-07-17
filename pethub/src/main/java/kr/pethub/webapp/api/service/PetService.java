package kr.pethub.webapp.api.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pethub.webapp.api.dao.PetDao;
import kr.pethub.webapp.api.model.SiteLinkData;

@Service
public class PetService {

	@Autowired
	private PetDao petDao;


	public int selectPetCount(String searchString) {
		return petDao.selectPetCount(searchString);
	}

	public List<SiteLinkData> selectPetList(SiteLinkData siteLinkData) {
		
		siteLinkData.setTotalRow(selectPetCount(""));

		List<SiteLinkData> list =  petDao.selectPetList(siteLinkData);
		
		for( SiteLinkData sd : list) {
			
			if( StringUtils.length( sd.getDataContent() )> 120) {
				sd.setDataContent( StringUtils.left(sd.getDataContent(), 120) + " ..." );
			}
		}
		
		return list;
	}

}
