package kr.pethub.webapp.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.pethub.webapp.api.model.SiteLinkData;
import kr.pethub.webapp.api.service.PetService;


@RestController
@RequestMapping("/api")
public class PetController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private PetService petService;
	
	/**
	 * 펫 목록
	 * @return
	 */
	@RequestMapping(value="petList", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object>  petList() {
		
		Map<String, Object> map = new HashMap<String, Object>();
				
//		int dataCount  = petService.selectPetCount("");
//		List<SiteLinkData> list = petService.selectPetList("");

//		 map.put("dataCount", dataCount);
//		 map.put("dataList", list);
		 
		 return map;
		
	} 
	
	
	
}