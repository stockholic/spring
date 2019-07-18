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


	/**
	 * 검색 수	
	 * @param siteLinkData
	 * @return
	 */
	public int selectPetCount(SiteLinkData siteLinkData) {
		return petDao.selectPetCount(siteLinkData);
	}

	/**
	 * 검색 목록
	 * @param siteLinkData
	 * @return
	 */
	public List<SiteLinkData> selectPetList(SiteLinkData siteLinkData) {
		
		siteLinkData.setTotalRow(selectPetCount(siteLinkData));

		List<SiteLinkData> list =  petDao.selectPetList(siteLinkData);
		
		for( SiteLinkData sd : list) {
			
			//검색어 하이라이팅
			if( StringUtils.isNotEmpty(siteLinkData.getDataTitle()) ) {
				sd.setDataTitle( sd.getDataTitle().replaceAll(siteLinkData.getDataTitle(),"<em>" + siteLinkData.getDataTitle() + "</em>") );
			}
			
			//내용 말줄임
			if( StringUtils.length( sd.getDataContent() )> 120) {
				sd.setDataContent( StringUtils.left(sd.getDataContent(), 120) + " ..." );
			}
		}
		
		return list;
	}

}
