package controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@Controller
public class RegisterController {
	
	private MemberRegisterService memberRegisterService;
	
	public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}
	
	
	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}
	
	@PostMapping("/register/step2")
	public String hadleStep2(@RequestParam(value="agree",defaultValue="false") boolean val,Model model) {
		if(!val) {
			return "register/step1";
		}
		model.addAttribute("registerRequest",new RegisterRequest());
		return "register/step2";
	}
	
	@GetMapping("/register/step2")
	public String handleStep2Get() {
		
		return "redirect:/register/step1";
	}
	
	@PostMapping("/register/step3")
	public String handleStep3(@Valid RegisterRequest regReq,Errors errors) {	//@ModelAttribute("forData") 이런식으로 jsp에서 사용할 속성값 이름을 변경도 가능하다
	//	new RegisterRequestValidator().validate(regReq,errors);	//RegisterRequestValidator 객체를 생성하여 validate를 실행한다 그러면서 커멘드 객체와 error객체를 넘기면서 
		if(errors.hasErrors())									//커멘드 객체의 값이 올바른지 검사하고 errors객체를 통해 결과를 받는다
				return "register/step2";
		
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
			
		}catch(DuplicateMemberException ex) {
			errors.rejectValue("email", "duplicate");  	//이메일 중복 에러를 추가하기 위해 "email"  프로퍼티의 에러 코드로 "duplicate"를 추가한다	
			
			return "register/step2";
		}
	}
	
	//@InitBinder
	//protected void initBinder(WebDataBinder binder) {
	//	binder.setValidator(new RegisterRequestValidator());	
		}	//InitBinder 애노테이션을 적용한 메서드는 WebDataBinder 타입 파라미터를 갖는데  WebDataBinder 타입파라미터를 갖는데 setValidator를 이용해 컨트롤러 범위에 적용할 Validator설정가능
			//RegisterRequest타입을 지원하는 RegisterRequestVlaidtor를 컨트롤러 범위 Validtor로 설정했으므로 Valid 애노테잇녀을 붙인 RegisterRequest 검증할 떄
			//이 Validator를 사용한다.
