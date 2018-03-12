package edu.doubler.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.doubler.login.domain.AppleUser;
import edu.doubler.login.domain.UserDTO;
import edu.doubler.login.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping(value="/")
	public String Launcher(Model model){
		
		// 도메인 객체 - 사과
		model.addAttribute("AppleUser", new AppleUser());
		
		return "/login_view/login_form_apple";
	}
	
	// -- [ 로그인 처리 ]
	@RequestMapping(value="/loginProcess")
	public String loginProcess(
	HttpSession session,
	@ModelAttribute("AppleUser") AppleUser appleUser){
		
		if(session.getAttribute("loginSession") != null)
			session.removeAttribute("loginSession");
		
		UserDTO user = loginService.getUser(appleUser);

		// check = true : 로그인 완료
		// key, value 쌍으로 값 형성
		if(user != null)
			session.setAttribute("loginSession", user);
		
		return "redirect:/";
	}
	
	// -- [ 로그아웃 처리 ]
	@RequestMapping(value="/logoutProcess")
	public String logoutProcess(HttpSession session){
		// 세션 객체 무효화 : http://blog.naver.com/PostView.nhn?blogId=zino1187&logNo=110025698379&categoryNo=17&viewDate=&currentPage=1&listtype=0
		// 무효화 이후 해당 세션 객체의 메소드 사용 불가
		// or session.removeAttribute(""); 가능
		session.invalidate();
		
		return "redirect:/";
	}
	
	// -- [ 게시판 이동 ]
	@RequestMapping(value="/board")
	public String goToBoard(HttpServletRequest request, Model model){
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginSession") != null)
			return "board_view/board_list_screen";
		
		return "redirect:/";
	}
}