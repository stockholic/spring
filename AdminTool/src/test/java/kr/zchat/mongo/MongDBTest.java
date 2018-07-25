package kr.zchat.mongo;

import java.util.List;



import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import kr.zchat.BaseTestCase;


public class MongDBTest extends BaseTestCase{

    @Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void save() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		User user = new User();
		user.setUserId("merong");
		user.setUserNm("메롱");
		mongoTemplate.save(user, "users");
		logger.debug("id : {}",user.getId());
		Assert.assertNotNull(user.getId());
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void findList() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		Query queryCount= new Query(Criteria.where("userId").is("merong"));
		logger.debug("count : {}", mongoTemplate.count(queryCount, User.class));
		
		
		Pageable page = new PageRequest(0, 10);
		Query queryList = new Query(Criteria.where("userId").is("merong"))
							.with(new Sort(Sort.Direction.DESC, "id"))
							.with(page);
		;
		
		List<User> users = mongoTemplate.find(queryList, User.class);
		
		for(User user : users){
			logger.debug("id : {}, userId : {}, userNm : {}",user.getId(), user.getUserId(),user.getUserNm());
		}
		
		logger.debug("-------------------------------------------------------------------------------> end");
		
	}
	
	@Test
	public void findOne() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		Query query = new Query(Criteria.where("id").is("59ba1374a4542700b06be28a"));
		User user = mongoTemplate.findOne(query, User.class);
		
		logger.debug("user : {}",user.getUserNm());
		
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}

	
	
	@Test
	public void update() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		Query query = new Query(Criteria.where("id").is("59ba1374a4542700b06be28a"));
		mongoTemplate.updateFirst(query,Update.update("userNm", "하하하하"),User.class);
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void remove() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		Query query = new Query(Criteria.where("id").is("5965b3f88592081090ba5585"));
		mongoTemplate.remove(query, User.class);
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	

}
