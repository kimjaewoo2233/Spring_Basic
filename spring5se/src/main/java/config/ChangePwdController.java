package config;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.ChangePwdCommand;
import controller.ChangePwdCommandValidator;
import spring.Authinfo;
import spring.ChangePasswordService;
import spring.WrongIdPasswordException;


@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {
		private ChangePasswordService changePasswordService;
		
		public void setChangePasswordService(ChangePasswordService changePasswordService) {
				this.changePasswordService = changePasswordService;
		}
		
		@GetMapping
		public String form(@ModelAttribute("command") ChangePwdCommand pwdcmd) {
					return "edit/changePwdForm";
		}
		@PostMapping
		public String submit(@ModelAttribute("command") ChangePwdCommand pwdcmd,Errors error,HttpSession session
						,HttpServletResponse resp) {
					new ChangePwdCommandValidator().validate(pwdcmd, error);	
					if(error.hasErrors()) {
						return "edit/changePwdForm";
					}
					Authinfo authinfo = (Authinfo)session.getAttribute("authinfo");
					try {
						changePasswordService.changePassword(
								authinfo.getEmail(),
								pwdcmd.getCurrentPassword(),
								pwdcmd.getNewPassword()
								);
						return "edit/changePwd";
					}catch(WrongIdPasswordException e) {
						error.rejectValue("currentPassword", "notMatching");
						return "edit/changePwdForm";
					}
		}
		
		

}
