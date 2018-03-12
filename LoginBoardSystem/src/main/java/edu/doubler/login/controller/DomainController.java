package edu.doubler.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.doubler.login.domain.UserDTO;
import edu.doubler.login.service.LoginService;

@Controller
@RequestMapping("/Domain")
public class DomainController {
	
	@Autowired
	LoginService loginService;
	
	// -- [ 도메인 이동 ]
	@RequestMapping("/{domainName}")
	public String redirectDomain(
	@PathVariable("domainName") String domainName,
	HttpServletRequest request,
	RedirectAttributes redirectAttributes){

		// 리다이렉트시 값 전달 : 암호화도 같이 진행
		// reference : http://noveloper.github.io/blog/spring/2015/02/16/how-transport-parateter-when-redirect.html
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("loginSession");
		String cipherKey = loginService.getCipherTextOnAES256(user.getKey());
		
		redirectAttributes.addAttribute("commonnessKey", cipherKey);
		
		switch(domainName){

			case "banana":
				return "redirect:http://localhost:8180/banana";
				
			case "cherry":
				return "redirect:http://localhost:8190/cherry";
				
			default:
				return null;

		}
	}
}
