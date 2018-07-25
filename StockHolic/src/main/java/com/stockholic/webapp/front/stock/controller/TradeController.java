package com.stockholic.webapp.front.stock.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.stockholic.core.authority.Auth;
import com.stockholic.core.utils.DateUtil;
import com.stockholic.core.utils.SysUtil;
import com.stockholic.webapp.front.stock.model.StockCode;
import com.stockholic.webapp.front.stock.model.Trade;
import com.stockholic.webapp.front.stock.service.FileService;
import com.stockholic.webapp.front.stock.service.TradeService;

@Controller
@RequestMapping("/stock")
public class TradeController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 주식종목 코드
	 * @return
	 */
	@RequestMapping(value= "/code",   produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StockCode>> getForPostObjectJson() {
		
		List<StockCode> stockCodeList = tradeService.selectStockCodeList();
		
		return new ResponseEntity<List<StockCode>>(stockCodeList, HttpStatus.CREATED);
	} 
	
	/**
	 * 거래 목록
	 * @param board
	 * @param auth
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/trade/list")
	public String TradeDetailList(@ModelAttribute Trade trade, Auth auth, Model model) {
		
		trade.setUserId(auth.getUserId());
		List<Trade> list = tradeService.selectTradeList(trade);
		model.addAttribute("list", list);
		return "front:stock/tradeList";
	} 
	
	/**
	 *  거래 상세
	 * @param trade
	 * @param auth
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/trade/view")
	public String TradeDetail(@ModelAttribute Trade trade, Auth auth, Model model) {
		
		trade.setUserId(auth.getUserId());
		model.addAttribute("tradeInfo", tradeService.selectTrade(trade));
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("fileTp", "stock");
		map.put("fileRefSrl", trade.getTradeSrl());
		model.addAttribute("fileList", fileService.selectFileList(map));
		return "front:stock/tradeView";
	} 
	
	/**
	 *  거래 등록 폼
	 * @param trade
	 * @param action
	 * @param auth
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/trade/{action}/form")
	public String inserTradeForm(@ModelAttribute Trade trade, @PathVariable String action, Auth auth, Model model) {
		
		//수정 폼
		if("insert".equals(action)){
			Trade tradeInfo = new Trade();
			tradeInfo.setTradeDt(DateUtil.getDate());
			model.addAttribute("tradeInfo", tradeInfo);
		}else if("update".equals(action)){
			trade.setUserId(auth.getUserId());
			model.addAttribute("tradeInfo", tradeService.selectTrade(trade));
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("fileTp", "stock");
			map.put("fileRefSrl", trade.getTradeSrl());
			model.addAttribute("fileList", fileService.selectFileList(map));
		}
		
		 return "front:stock/tradeForm";
		 
	} 
	
	/**
	 * 거래 등록
	 * @param request
	 * @param trade
	 * @param auth
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/trade/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object>  insertTrade(HttpServletRequest request, Trade trade,  Auth auth) throws JsonParseException, JsonMappingException, IOException {
		
		trade.setIp(SysUtil.getRemoteIp(request));
		trade.setUserId(auth.getUserId());
		tradeService.insertTrade(trade);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tradeSrl", trade.getTradeSrl());	
		
		return map;
	} 
	
	/**
	 *  거래 수정
	 * @param request
	 * @param trade
	 * @param auth
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/trade/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Object> updateTrade(HttpServletRequest request, Trade trade,  Auth auth) throws JsonParseException, JsonMappingException, IOException{
		
		trade.setIp(SysUtil.getRemoteIp(request));
		trade.setUserId(auth.getUserId());
		tradeService.updateTrade(trade);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tradeSrl", trade.getTradeSrl());	
		
		return map;
	} 
	
	
	/**
	 * 거래 삭제
	 * @param trade
	 * @param auth
	 */
	@ResponseBody
	@RequestMapping(value="/trade/delete", method = RequestMethod.POST)
	public void deleteTradeDetail(Trade trade, Auth auth) {
		trade.setUserId(auth.getUserId());
		tradeService.deleteTrade(trade);
	} 
	
	
	/**
	 * 포인트 수정
	 * @param trade
	 * @param auth
	 */
	@ResponseBody
	@RequestMapping(value="/trade/updatePoint", method = RequestMethod.POST)
	public void updatePoint(Trade trade, Auth auth) {
		trade.setUserId(auth.getUserId());
		tradeService.updatePoint(trade);
	} 
	
	/**
	 * 전체 수익
	 * @param auth
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/trade/profitByTotal")
	public String profitByTotal(Auth auth, Model model) {
		model.addAttribute("profit", tradeService.selectProfitByTotal(auth.getUserId()));
		return "front:stock/profitByTotal";
	} 
	
	/**
	 * 종목별 수익
	 * @param auth
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/trade/profitByStock")
	public String profitByStock(Auth auth, Model model) {
		model.addAttribute("profit", tradeService.selectProfitByStock(auth.getUserId()));
		return "front:stock/profitByStock";
	} 
	
	/**
	 * 년도별 수익
	 * @param auth
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/trade/profitByYear")
	public String profitByYear(Auth auth, Model model) {
		model.addAttribute("profit", tradeService.selectProfitByYear(auth.getUserId()));
		return "front:stock/profitByYear";
	} 
	
	/**
	 * 월별 수익
	 * @param auth
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/trade/profitByMonth")
	public String profitByMonth(Auth auth, Model model) {
		model.addAttribute("profit", tradeService.selectProfitByMonth(auth.getUserId()));
		return "front:stock/profitByMonth";
	} 
	

}