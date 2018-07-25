package com.stockholic.webapp.front.stock.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockholic.core.annotation.CacheableStockCode;
import com.stockholic.core.module.model.FileInfo;
import com.stockholic.core.utils.FileManager;
import com.stockholic.webapp.front.board.dao.FileDao;
import com.stockholic.webapp.front.stock.dao.TradeDao;
import com.stockholic.webapp.front.stock.model.Profit;
import com.stockholic.webapp.front.stock.model.StockCode;
import com.stockholic.webapp.front.stock.model.Trade;


@Service
public class TradeService{
	
	@Value("${deal.file.path}") 
	private String dealFilePath;
	
	@Autowired
	TradeDao tradeDao;
	
	@Autowired
	private FileDao fileDao;
	
	/**
	 * 주식종목 코드
	 * @return
	 */
	@CacheableStockCode
	public List<StockCode> selectStockCodeList(){ 
		return  tradeDao.selectStockCodeList();
	}
	
	/**
	 * 거래 수
	 * @param selectBoardCount
	 * @return
	 */
	public int selectTradeCount(Trade trade){
		return  tradeDao.selectTradeCount(trade);
	}
	
	/**
	 * 거래 목록
	 * @param userId
	 * @return
	 */
	public List<Trade> selectTradeList(Trade trade){ 
		trade.setTotalRow(selectTradeCount(trade));
		return  tradeDao.selectTradeList(trade);
	}
	
	/**
	 * 거래 상세
	 * @param trade
	 * @return
	 */
	public Trade selectTrade(Trade trade){ 
		return  tradeDao.selectTrade(trade);
	}
	
	/**
	 * 거래 등록
	 * @param trade
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void insertTrade(Trade trade) throws JsonParseException, JsonMappingException, IOException{ 
		
		//거래 등록
		tradeDao.insertTrade(trade);

		//첨부파일 등록
		if(StringUtils.isNotEmpty(trade.getFileInfo())) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> map = mapper.readValue(trade.getFileInfo(), Map.class);
			List<Object> fileList = (List<Object>)map.get("insertFile");
			
			for(Object obj : fileList) {
				FileInfo fileInfo = mapper.convertValue(obj, FileInfo.class);
				fileInfo.setFileRefSrl(trade.getTradeSrl());
				fileInfo.setFileTp("stock");
				fileDao.insertFile(fileInfo);
			}
		}
		
	}
	
	/**
	 * 거래 수정
	 * @param trade
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public void updateTrade(Trade trade) throws JsonParseException, JsonMappingException, IOException{ 
		
		//거래 수정
		tradeDao.updateTrade(trade);

		if(StringUtils.isNotEmpty(trade.getFileInfo())) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> map = mapper.readValue(trade.getFileInfo(), Map.class);

			//첨부파일 등록
			List<Object> fileList = (List<Object>)map.get("insertFile");
			for(Object obj : fileList) {
				FileInfo fileInfo = mapper.convertValue(obj, FileInfo.class);
				fileInfo.setFileRefSrl(trade.getTradeSrl());
				fileInfo.setFileTp("stock");
				fileDao.insertFile(fileInfo);
			}
			
			//첨부파일 삭제
			List<Object> deleteList = (List<Object>)map.get("deleteFile");
			for(Object fileSrl : deleteList) {
				FileInfo  fileInfo = fileDao.selectFile((Integer)fileSrl);
				FileManager.fileDelete(fileInfo.getFilePath() + "/" + fileInfo.getFileSysNm());
				fileDao.deleteFile((Integer)fileSrl);
			}
			
		}
		
	}
	
	/**
	 * 거래 삭제
	 * @param trade
	 */
	@Transactional
	public void deleteTrade(Trade trade){ 
		int isDelete = tradeDao.deleteTrade(trade);
		
		if(isDelete > 0) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("fileTp", "stock");
			map.put("fileRefSrl", trade.getTradeSrl());
			
			List<FileInfo>  fileList = fileDao.selectFileList(map);
			for(FileInfo fileInfo : fileList) {
				FileManager.fileDelete(fileInfo.getFilePath() + "/" + fileInfo.getFileSysNm());
				fileDao.deleteFile(fileInfo.getFileSrl());
			}
		}
	}

	/**
	 * 포인트 수정
	 * @param trade
	 */
	public void updatePoint(Trade trade){ 
		 tradeDao.updatePoint(trade);
	}
	
	/**
	 * 전체 수익
	 * @param userId
	 * @return
	 */
	public Profit selectProfitByTotal(String userId){ 
		return  tradeDao.selectProfitByTotal(userId);
	}
	
	/**
	 * 종목별 수익
	 * @param userId
	 * @return
	 */
	public List<Profit> selectProfitByStock(String userId){ 
		return  tradeDao.selectProfitByStock(userId);
	}
	
	/**
	 * 년도별 수익
	 * @param userId
	 * @return
	 */
	public List<Profit> selectProfitByYear(String userId){ 
		return  tradeDao.selectProfitByYear(userId);
	}
	
	/**
	 * 월별 수익
	 * @param userId
	 * @return
	 */
	public List<Profit> selectProfitByMonth(String userId){ 
		return  tradeDao.selectProfitByMonth(userId);
	}
	
	
}
