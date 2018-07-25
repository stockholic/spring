package kr.zchat.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

public class LdapUserAttributeMapper implements AttributesMapper<LdapUser>{

	@Override
	public LdapUser mapFromAttributes(Attributes attributes) throws NamingException {
		
		LdapUser ldapUser = new LdapUser();
		if(attributes.get("uid") != null) ldapUser.setUid((String)attributes.get("uid").get());
		if(attributes.get("cn") != null) ldapUser.setCn((String)attributes.get("cn").get());
		if(attributes.get("mail") != null) ldapUser.setMail((String)attributes.get("mail").get());
		
		return ldapUser;
	}

}
