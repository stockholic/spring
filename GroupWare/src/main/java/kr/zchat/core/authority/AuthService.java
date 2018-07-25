package kr.zchat.core.authority;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.zchat.web.admin.mail.dao.UserDao;

@Service
public class AuthService  implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException, DataAccessException {
    	
		//로그인 정보
		Map<String, String> user = new HashMap<String, String>();
		user.put("userId", userId);
		user.put("useYn", "Y");
		Auth auth = userDao.selectUserInfo(user);
		
		//회원 권한
		if(auth != null){
			
			List<String> roleList = new ArrayList<String>();
			List<Auth> authList = userDao.selectUserAuthList(userId);
			
			for(Auth lst :  authList ){
				roleList.add( lst.getRole());
			}
			
			auth.setRoleList(roleList);
		}

		return new AuthInfo(auth);
    }
    
}