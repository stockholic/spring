package kr.zchat.ldap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.naming.Name;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;

import kr.zchat.BaseTestCase;

public class LdapTest extends BaseTestCase {

	@Autowired
	private LdapTemplate ldapTemplate;
	
	 @Autowired
	 private ContextSource contextSource;
	
	private String  ou = "dev";
	private String uid = "merong";
	
	
	@Test
	public void LDAP_OU_목록() {

		logger.debug("-------------------------------------------------------------------------------> start");

		List<String> list = ldapTemplate.search(
			"",
			"objectclass=organizationalUnit",
			(AttributesMapper<String>) attrs -> attrs.get("ou") == null ? "" : (String) attrs.get("ou").get()
		);

		logger.debug("count : {}", list.size());
		for (String ou : list) {
			logger.debug("ou : {}", ou);
		}

		logger.debug("-------------------------------------------------------------------------------> end");
	}


	@Test
	public void LDAP_사용자_목록() {

		logger.debug("-------------------------------------------------------------------------------> start");

		AndFilter andFilter = new AndFilter();
//		andFilter.and(new EqualsFilter("uid", "jspark"));
		andFilter.and(new EqualsFilter("objectclass", "inetOrgPerson"));

		List<LdapUser> list = ldapTemplate.search(
				"ou=" + ou,
				andFilter.encode(), 
				new LdapUserAttributeMapper());

		logger.debug("count : {}", list.size());
		for (LdapUser user : list) {
			logger.debug("uid : {}, cn : {}, mail : {}", user.getUid(), user.getCn(), user.getMail());
		}
		

		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void LDAP_사용자_등록() {

		logger.debug("-------------------------------------------------------------------------------> start");

		String firstNm = "Park";				//first name 성		
		String lastNm = "MG";				//last name 이름
		String cn = firstNm + " " + lastNm;
		String mail = "abcd@efg.com";
		String password = "1234";
		
		Name dn = LdapNameBuilder.newInstance().add("ou", ou).add("uid", uid).build();
		DirContextAdapter context = new DirContextAdapter(dn);

		context.setAttributeValues("objectclass", new String[] { "top", "posixAccount", "inetOrgPerson" });
		context.setAttributeValue("uid", uid);		
		context.setAttributeValue("uidNumber", "0");		
		context.setAttributeValue("gidNumber", "0");		
		context.setAttributeValue("givenName", firstNm); 	//first name 성		
		context.setAttributeValue("sn", lastNm);				//last name 이름
		context.setAttributeValue("cn", cn);	
		context.setAttributeValue("mail",mail);	
		context.setAttributeValue("homeDirectory", "/ldap/user/" + uid);
		context.setAttributeValue("userPassword", digestSHA(password));

		ldapTemplate.bind(context);

		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void LDAP_사용자_수정() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		String firstNm = "박";				//first name 성		
		String lastNm = "메롱";				//last name 이름
		String cn = firstNm + " " + lastNm;
		String mail = "abcd@efg.net";
		String password = "1234";
		
		Name dn = LdapNameBuilder.newInstance().add("ou", ou).add("uid", uid).build();
		DirContextOperations context = ldapTemplate.lookupContext(dn);

		context.setAttributeValue("givenName", firstNm); 	//first name 성		
		context.setAttributeValue("sn", lastNm);								//last name 이름
		context.setAttributeValue("cn", cn);	
		context.setAttributeValue("mail", mail);	
		context.setAttributeValue("userPassword", digestSHA(password));

	    ldapTemplate.modifyAttributes(context);
	    
	    logger.debug("-------------------------------------------------------------------------------> end");
		
	}
	
	@Test
	public void LDAP_사용자_삭제() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		Name dn = LdapNameBuilder.newInstance().add("ou", ou).add("uid", uid).build();
	    ldapTemplate.unbind(dn);
	    
	    logger.debug("-------------------------------------------------------------------------------> end");
		
	}
	
	private String digestSHA(final String password) {
        String base64;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            base64 = Base64
              .getEncoder()
              .encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "{SHA}" + base64;
    }
	

}
