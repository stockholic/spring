package com.stockholic.webapp.front.stock.model;

import java.util.Date;

import com.stockholic.core.module.model.Pagination;

public class Trade  extends Pagination{

	private Integer tradeSrl;
	private String stockCd;
	private String stockNm;
	private int tradeBuyPrc;
	private int tradeSellPrc;
	private int tradeEa;
	private String tradeTp;
	private int tax;
	private int fee;
	private String memo;
	private String ip;
	private String userId;
	private Date  regDt;
	private Date uptDt;
	private String tradeDt;
	private int point;
	
	private String searchTradeTp;
	private String searchStartDt;
	private String searchEndDt;
	private String searchPoint;
	private int fileCnt;
	private String fileInfo;
	
	public Integer getTradeSrl() {
		return tradeSrl;
	}
	public void setTradeSrl(Integer tradeSrl) {
		this.tradeSrl = tradeSrl;
	}
	public String getStockCd() {
		return stockCd;
	}
	public void setStockCd(String stockCd) {
		this.stockCd = stockCd;
	}
	public String getStockNm() {
		return stockNm;
	}
	public void setStockNm(String stockNm) {
		this.stockNm = stockNm;
	}
	public int getTradeBuyPrc() {
		return tradeBuyPrc;
	}
	public void setTradeBuyPrc(int tradeBuyPrc) {
		this.tradeBuyPrc = tradeBuyPrc;
	}
	public int getTradeSellPrc() {
		return tradeSellPrc;
	}
	public void setTradeSellPrc(int tradeSellPrc) {
		this.tradeSellPrc = tradeSellPrc;
	}
	public int getTradeEa() {
		return tradeEa;
	}
	public void setTradeEa(int tradeEa) {
		this.tradeEa = tradeEa;
	}
	public String getTradeTp() {
		return tradeTp;
	}
	public void setTradeTp(String tradeTp) {
		this.tradeTp = tradeTp;
	}
	public int getTax() {
		return tax;
	}
	public void setTax(int tax) {
		this.tax = tax;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public Date getUptDt() {
		return uptDt;
	}
	public void setUptDt(Date uptDt) {
		this.uptDt = uptDt;
	}
	public String getSearchTradeTp() {
		return searchTradeTp;
	}
	public void setSearchTradeTp(String searchTradeTp) {
		this.searchTradeTp = searchTradeTp;
	}
	public String getSearchStartDt() {
		return searchStartDt;
	}
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}
	public String getSearchEndDt() {
		return searchEndDt;
	}
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	public String getSearchPoint() {
		return searchPoint;
	}
	public void setSearchPoint(String searchPoint) {
		this.searchPoint = searchPoint;
	}
	public int getFileCnt() {
		return fileCnt;
	}
	public void setFileCnt(int fileCnt) {
		this.fileCnt = fileCnt;
	}
	public String getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}
	public String getTradeDt() {
		return tradeDt;
	}
	public void setTradeDt(String tradeDt) {
		this.tradeDt = tradeDt;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
}
