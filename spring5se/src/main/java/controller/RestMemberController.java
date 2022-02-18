package controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.DuplicateMemberException;
import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@RestController
public class RestMemberController {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberRegisterService registerService;
	
	
	@GetMapping("/api/members")
	public List<Member> members(){
		return memberDao.selectAll();
	}
	
	@GetMapping("/api/members/{id}")
	public ResponseEntity<?> member(@PathVariable Long id){
			Member member = memberDao.selectById(id);
			if(member == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
			}
			return ResponseEntity.status(HttpStatus.OK).body(member);
			//ResponseEntity의 status로 지정한 값을 응답 상태 코드로 사용한다 NOT_FOUND는 404 상태 코드와 함께 JSon 형식으로 응답 데이터를 전송
			//ResponseEntity.status(상태).body(객체)
	}
	
	@PostMapping("/api/members")
	public ResponseEntity<Object> newMember(@RequestBody @Valid RegisterRequest regReq,HttpServletResponse response) throws IOException{
		try {
			Long newMemberID = registerService.regist(regReq);
		
			URI uri = URI.create("/api/members"+newMemberID);
			
			//	response.setHeader("Location","/api/members/"+newMemberID);
		//	response.setStatus(HttpServletResponse.SC_CREATED);	//성공하면 201을 반환함 
			return ResponseEntity.created(uri).build();
		}catch(DuplicateMemberException dupEx) {
			response.sendError(HttpServletResponse.SC_CONFLICT);//예외시 409(CONFLICT)를 리턴
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoData(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
	}
}
