package com.stockholic.webapp.front.stock.model;

public class Profit {

	private String stockCd;
	private String tradeYear;
	private String tradeMonth;
	private String stockNm;
	private String userId;
	private long profit;
	private int fee;
	private int tax;
	
	public String getStockCd() {
		return stockCd;
	}
	public void setStockCd(String stockCd) {
		this.stockCd = stockCd;
	}
	public String getTradeYear() {
		return tradeYear;
	}
	public void setTradeYear(String tradeYear) {
		this.tradeYear = tradeYear;
	}
	public String getTradeMonth() {
		return tradeMonth;
	}
	public void setTradeMonth(String tradeMonth) {
		this.tradeMonth = tradeMonth;
	}
	public String getStockNm() {
		return stockNm;
	}
	public void setStockNm(String stockNm) {
		this.stockNm = stockNm;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getProfit() {
		return profit;
	}
	public void setProfit(long profit) {
		this.profit = profit;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public int getTax() {
		return tax;
	}
	public void setTax(int tax) {
		this.tax = tax;
	}

	
}

