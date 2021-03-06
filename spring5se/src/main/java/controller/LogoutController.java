package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
		
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
			HttpSession session = req.getSession();
			session.invalidate();
			return "redirect:/main";
	}
}
