//package com.doubler.oauth.service;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Calendar;
//import java.util.Date;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//

/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * 
 * JWT 에 대해서 더 공부하고 써보기.
 * 
 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/

//public class OAuthJWTExample {
//	public static void generateToken(){
//		/**
//		 * 반환 내용
//		 * - access_token
//		 * - refresh_token
//		 * - token_type : bearer
//		 * - expires_in : 3600
//		 * 
//		 * 이 JSON 형태로 이루어져있다.
//		 * **/
//		
//		/**
//		 * JWT 설명글 : https://medium.com/@OutOfBedlam/jwt-%EC%9E%90%EB%B0%94-%EA%B0%80%EC%9D%B4%EB%93%9C-53ccd7b2ba10
//		 * https://github.com/auth0/java-jwt 을 참고
//		 * **/
//		try{
//			
//			/**
//			 * 
//			 * (1) JWT.create() 를 호출하여, JWTCreator 인스턴스 생성
//			 * (2) 사용자 정의 클레임(권한)을 설정 (사용자 리소스 / 스코프 / 만료시간)
//			 * (2-1) Issuer, Subject, Expiration, ID 같은 토큰의 내부 권한 클레임 설정
//			 * **/
//			Calendar calendar = Calendar.getInstance();
//			Date nowDate = calendar.getTime();
//			calendar.add(Calendar.MINUTE, 15);
//			Date expirationDate = calendar.getTime();
//			
//			Algorithm algorithm = Algorithm.HMAC256("secret");
//			String value = JWT.create()
//					.withIssuer("doubleBoard")
//					.withExpiresAt(expirationDate)
//					.withClaim("serviceUrl", "")
//					.withClaim("exp", expirationDate)
//					.withClaim("scope", "read")
//					.withKeyId("asdad")
//					.sign(algorithm);
//			
//			DecodedJWT jwt = JWT.decode(value);
//			System.out.println("getToken : " + jwt.getToken());
//			System.out.println("getType : " + jwt.getType());
//			System.out.println("getIssuer : " + jwt.getIssuer());
//			System.out.println("getAlgorithm : " + jwt.getAlgorithm());
//			System.out.println("=============");
//			System.out.println("[ Header ]");
//			System.out.println("getHeader : " + jwt.getHeader());
//			System.out.println("=============");
//			System.out.println("[ Payload ]");
//			System.out.println("getPayload : " + jwt.getPayload());
//			System.out.println("getClaim : serviceUrl > " + jwt.getClaim("serviceUrl").asString());
//			System.out.println("getClaim : exp > " + jwt.getClaim("exp").asLong());
//			System.out.println("getClaim : scope > " + jwt.getClaim("scope").asString());
//			System.out.println("=============");
//			System.out.println("[ Signature ]");
//			System.out.println("getSignature : " + jwt.getSignature());
//			System.out.println("=============");
//			System.out.println("getExpiresAt : " + jwt.getExpiresAt());
//			
//			System.out.println("getContentType : " + jwt.getContentType());
//			System.out.println("getId : " + jwt.getId());
//			System.out.println("getKeyId : " + jwt.getKeyId());
//			System.out.println("getSubject : " + jwt.getSubject());
//			System.out.println("getIssuedAt : " + jwt.getIssuedAt());
//		}
//		catch(UnsupportedEncodingException e){
//			System.out.println(e.getMessage());
//		}
//		catch(JWTCreationException e){
//			System.out.println(e.getMessage());
//		}
//	}
//}
