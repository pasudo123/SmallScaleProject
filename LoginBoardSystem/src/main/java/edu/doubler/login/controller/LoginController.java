package edu.doubler.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.doubler.login.domain.EnumDomainName;
import edu.doubler.login.service.LoginService;

@Controller
public class LoginController {
	
	@RequestMapping(value="/")
	public String login(HttpServletRequest request){
		
//		아래와 같은 주소가 있을 경우
//		http://localhost:8080/test/index.jsp
//		request.getRequestURI();   //프로젝트경로부터 파일까지의 경로값을 얻어옴 (/test/index.jsp)
//		request.getContextPath();  //프로젝트의 경로값만 가져옴(/test)
//		request.getRequestURL();   //전체 경로를 가져옴 (http://localhost:8080/test/index.jsp)
//		request.getServletPath();  //파일명 (/index.jsp)
		
		int portNumber = request.getServerPort();
		
		switch(portNumber){
		case 8181:
			return EnumDomainName.APPLE.getViewPath();
		case 8180:
			return EnumDomainName.BANANA.getViewPath();
		default:
			return null;
		}
	}
}