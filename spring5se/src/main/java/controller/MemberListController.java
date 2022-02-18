package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.Member;
import spring.MemberDao;

@Controller
public class MemberListController {
	
	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberdao) {
		this.memberDao = memberdao;
	}
	
	@RequestMapping("/member")
	public String list(@ModelAttribute("cmd") ListCommand list,Errors errors,Model model) {
		if(errors.hasErrors()) {
			return "member/memberList";	//error400이 발생하면 이상한 페이지가 아닌 다시 이 페이지로 보내서 에러났다는 것을 아릶
		}
		
		if(list.getFrom() != null && list.getTo() != null) {
				List<Member> members = memberDao.selectByRegDate(list.getFrom(), list.getTo());
				model.addAttribute("members",members);
		}
		return "member/memberList";
	}
}
