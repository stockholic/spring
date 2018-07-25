package kr.zchat.web.admin.mail.vo;

import java.util.Date;

public class Mail {

	private String mailSeqno;
	private String mailTitle;
	private String useYn;
	private String createId;
	private Date createDt;
	private Date updateDt;
	private String updateId;
	
	public String getMailSeqno() {
		return mailSeqno;
	}
	public void setMailSeqno(String mailSeqno) {
		this.mailSeqno = mailSeqno;
	}
	public String getMailTitle() {
		return mailTitle;
	}
	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	
}
