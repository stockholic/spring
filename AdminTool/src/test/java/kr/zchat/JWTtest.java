package kr.zchat;

import static org.hamcrest.CoreMatchers.is;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.Test;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JWTtest extends BaseTestCase{
	
	
	@Test
	public void createJwt() throws JOSEException, NoSuchAlgorithmException {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		
//		KeyGenerator gen = KeyGenerator.getInstance("AES");
//		gen.init(256); /* 256-bit AES */
//		SecretKey secret = gen.generateKey();
//		byte[] binary = secret.getEncoded();
//		String key = String.format("%032X", new BigInteger(+1, binary));
//		logger.debug("key : {}",key);
		
		
		String key = "6BD52264C9DE3B87C92D41ABCFA8C6796FCCD3ACB49225BCF3CE7BF7249E86AF";
		
		JWSSigner signer = new MACSigner(key);
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
		                .claim("name", "merong")
		                .claim("admin", true)
		                .build();
		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
		signedJWT.sign(signer);
		 
		String jwtString = signedJWT.serialize();
		
		logger.debug("create token : {}", jwtString);
		
		
		
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	
	@Test
	public void checkJwt() throws ParseException, JOSEException  {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		String key = "6BD52264C9DE3B87C92D41ABCFA8C6796FCCD3ACB49225BCF3CE7BF7249E86AF";
		String jwtString = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoibWVyb25nIiwiYWRtaW4iOnRydWV9.TBt6nytbo6V_x9c3d6Fy1ASfzO9ey6M9EV3xv7Uifls";
		
		JWT signedJWT = (SignedJWT)SignedJWT.parse(jwtString);
		System.out.println(signedJWT.getJWTClaimsSet());
		System.out.println(signedJWT.getHeader().getAlgorithm());

		// verification
		JWSVerifier verifier = new MACVerifier(key);
		
		logger.debug("verifier : {}", ((JWSObject) signedJWT).verify(verifier));
		
		assertThat(((JWSObject) signedJWT).verify(verifier), is(true));

		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
}
