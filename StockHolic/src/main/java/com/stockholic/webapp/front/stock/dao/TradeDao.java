package com.stockholic.webapp.front.stock.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.stockholic.core.module.dao.MultiSqlSessionDaoSupport;
import com.stockholic.webapp.front.stock.model.Profit;
import com.stockholic.webapp.front.stock.model.StockCode;
import com.stockholic.webapp.front.stock.model.Trade;

@Repository
public class TradeDao extends MultiSqlSessionDaoSupport{	
	
	public List<StockCode> selectStockCodeList(){
		return  selectList("selectStockCodeList");
	}
	
	public int selectTradeCount(Trade trade){
		return getInt("selectTradeCount",trade);
	}
	
	public List<Trade> selectTradeList(Trade trade ){
		return  selectList("selectTradeList", trade);
	}

	public Trade selectTrade(Trade trade){
		return  selectOne("selectTrade", trade);
	}
	
	public int insertTrade(Trade trade){
		return  insert("insertTrade",trade);
	}
	
	public int updateTrade(Trade trade){
		return  update("updateTrade",trade);
	}
	
	public int deleteTrade(Trade trade){
		return  delete("deleteTrade",trade);
	}
	
	public int updatePoint(Trade trade){
		return  update("updatePoint",trade);
	}
	
	public Profit selectProfitByTotal(String userId){
		return  selectOne("selectProfitByTotal", userId);
	}
	
	public List<Profit> selectProfitByStock(String userId){
		return  selectList("selectProfitByStock", userId);
	}
	
	public List<Profit> selectProfitByYear(String userId){
		return  selectList("selectProfitByYear", userId);
	}
	
	public List<Profit> selectProfitByMonth(String userId){
		return  selectList("selectProfitByMonth", userId);
	}
	
}
