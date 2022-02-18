package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthCheckInterceptor implements HandlerInterceptor {

		@Override
		public boolean preHandle(HttpServletRequest req,HttpServletResponse resp,Object handler)
								throws Exception{
			HttpSession session = req.getSession(false);
			//getSession(),getSession(true) 는 HttpSession 이 존재하면 현재 HttpSession 을 반환하고 존재하지 않으면 새로 이 새션을 생성
			//getSession(false) HttpSession이 존재하면 현재 HttpSession을 반환하고 존재하지 않으면 새로이 생성하지 않고 그냥 null을 반환함 
			if(session != null) {
				Object authInfo = session.getAttribute("authInfo");
				if(authInfo != null) {
					return true;
				}
			}
			
			resp.sendRedirect(req.getContextPath()+"/login");
			return false;
		}

}
 