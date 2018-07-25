package kr.zchat.ldap;

public class LdapUser {
	
	private String uid;			// user id
	private String cn;			// user name CN (Common Name) : KilDong Hong, SaRang Lee 와 같은 일반적인 이름들 
	private String firstNm;		// first name 성 (SN)
	private String lastNm;		// last name 이름 ()
	private String password;
	private String mail;
	private String ou;			// Organiztion Unit : 조직구분
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getFirstNm() {
		return firstNm;
	}
	public void setFirstNm(String firstNm) {
		this.firstNm = firstNm;
	}
	public String getLastNm() {
		return lastNm;
	}
	public void setLastNm(String lastNm) {
		this.lastNm = lastNm;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getOu() {
		return ou;
	}
	public void setOu(String ou) {
		this.ou = ou;
	}
	
}
