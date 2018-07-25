package kr.zchat.web.admin.mail.vo;

import java.util.List;

public class Email {

	private String box; 
	private String messageId;
	private String fileName;
	
	private Integer page;			//페이지 번호
	private int total;					//총목록수
	private int totalPage;			//페이지 번호
	private int start;					//시작 로우
	private int limit;					//마지막 로우
	
	private List<EmailMessage> inboxList;
	
	public String getBox() {
		return box == null || "".equals(box) ? "INBOX" : box;
	}
	public void setBox(String box) {
		this.box = box;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getPage() {
		return page == null ? 1 : page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<EmailMessage> getInboxList() {
		return inboxList;
	}
	public void setInboxList(List<EmailMessage> inboxList) {
		this.inboxList = inboxList;
	} 
 	
}
