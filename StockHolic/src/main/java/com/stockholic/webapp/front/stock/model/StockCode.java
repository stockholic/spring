package com.stockholic.webapp.front.stock.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockCode {

	@JsonProperty("value")
	private String stockNm;
	

	@JsonProperty("data")
	private String stockCd;

	public String getStockNm() {
		return stockNm;
	}

	public void setStockNm(String stockNm) {
		this.stockNm = stockNm;
	}

	public String getStockCd() {
		return stockCd;
	}

	public void setStockCd(String stockCd) {
		this.stockCd = stockCd;
	}
	
}
