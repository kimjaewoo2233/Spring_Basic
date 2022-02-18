package controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;

@Controller
public class MemberDetailController {
	@Autowired
	private MemberDao memberDao;
	
	@GetMapping("/member/{id}")
	public String detail(@PathVariable("id") Long memId,Model model) {
			Member member = memberDao.selectById(memId);
			if(member == null) {
				throw new MemberNotFoundException();
			}
			model.addAttribute("member",member);
			return "member/memberDetail";
	}
	
	@ExceptionHandler(TypeMismatchException.class)	//컨트롤러에서 발생한 익셉션을 직접 처리히고 싶다면
	public String handleTypeMismatchExcpetion () {	//Exception 애노테이션을 적용한 메서드를 구하면된다
		return "member/invalidid";	//여기에 있는 TypeMismatchException는 변수 값의 타입이 올바르지 않을 때 발생함
										//위에서 경로변수를 문자열로 주거나 그러면 일어나는 예외임
	}
	@ExceptionHandler(MemberNotFoundException.class)	//위 detail 메서드에서 예외가 발생하면
	public String handlerNotFoundException() {			//여기서 잡는다 
		return "member/noMember";						//ExceptionHandler는 해당 컨트롤러에서 발생한 익셉션만 처리한다
	}
}
