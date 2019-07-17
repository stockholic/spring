package kr.pethub.webapp.front.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.pethub.core.utils.StringUtil;
import kr.pethub.webapp.api.model.SiteLinkData;
import kr.pethub.webapp.api.service.PetService;


@Controller
@RequestMapping("/search")
public class SearchController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PetService petService;
	
	@RequestMapping(value= {"list", "list/{p}"}, method = RequestMethod.GET)
	public String list( @ModelAttribute SiteLinkData siteLinkData, @PathVariable(value="p",required = false) String p, Model model) {
		
		if( StringUtil.isRegex("^[0-9]{1,4}$",p) ) {
			siteLinkData.setPage(Integer.parseInt(p));
		}else {
			siteLinkData.setPage(1);
		}
		
		List<SiteLinkData> list = petService.selectPetList(siteLinkData);
		
		model.addAttribute("list", list);
		
		 return "front:search/searchList";
	} 

}