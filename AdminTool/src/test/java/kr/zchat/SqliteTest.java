package kr.zchat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.zchat.webapp.admin.site.model.User;
import kr.zchat.webapp.admin.site.service.UserService;

public class SqliteTest extends BaseTestCase{
	
	
	@Autowired
	private UserService userService;
	
	@Test
	public void SQLITE_테스트() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		logger.debug("count : {}", userService.selectUserCount(new User()));
		logger.debug("count : {}", userService.selectUserCount(new User()));
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
}
