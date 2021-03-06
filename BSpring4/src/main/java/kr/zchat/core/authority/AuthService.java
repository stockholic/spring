package kr.zchat.core.authority;


import java.util.ArrayList;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.zchat.webapp.admin.site.dao.AuthDao;

@Service
public class AuthService  implements UserDetailsService {
	
	@Autowired
	private AuthDao dao;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException, DataAccessException {
    	
    	
		//로그인 정보
		Map<String, String> user = new HashMap<String, String>();
		user.put("userId", userId);
		user.put("useYn", "Y");
		Auth dto = (Auth)this.dao.selectOne("getUserInfo",user);
		
		//회원 권한
		if(dto != null){
			
			List<String> roleList = new ArrayList<String>();
			List<Auth> authList = dao.selectList("getUserAuthList",userId);
			
			for(Auth lst :  authList ){
				roleList.add( lst.getRole());
			}
			
			dto.setRoleList(roleList);
		}

		return new AuthInfo(dto);
    }
    public int insertLoginLog(String tryId, String successYn, String remoteAddr, String comment) {
        
        Map<String, String> user = new HashMap<String, String>();
        user.put("tryId", StringUtils.substring(tryId, 0, 100));
        user.put("successYn", successYn);
        user.put("remoteAddr", remoteAddr);
        user.put("comment", comment);
        int result = this.dao.insert("insertLoginLog", user);
        
        return result;
    }
    
}