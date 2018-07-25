package kr.zchat.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class MongoDbConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient(env.getRequiredProperty("mdb.host"),	Integer.parseInt(env.getRequiredProperty("mdb.port")));
	}
	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {

		ServerAddress serverAddress = new ServerAddress(env.getRequiredProperty("mdb.host"), Integer.parseInt(env.getRequiredProperty("mdb.port")));

		// Set credentials
		MongoCredential credential = MongoCredential.createCredential(env.getRequiredProperty("mdb.username"), 
																								env.getRequiredProperty("mdb.database"),
																								env.getRequiredProperty("mdb.password").toCharArray());
		// Mongo Client
		MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));

		return new SimpleMongoDbFactory(mongoClient, env.getRequiredProperty("mdb.database"));

	}
	
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
//		return new MongoTemplate(mongo(), env.getRequiredProperty("mdb.database"));
		  return new MongoTemplate(mongoDbFactory());
	}

}