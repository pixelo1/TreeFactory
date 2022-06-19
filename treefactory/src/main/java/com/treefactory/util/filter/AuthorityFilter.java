package com.treefactory.util.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet Filter implementation class AuthorityFilter
 */
/*@WebFilter("/AuthorityFilter") 주석처리함 web.xml에서 한다*/

public class AuthorityFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//권한 맵에 URL = 권한 저장
	//변수 저장 MAP<URI, 권한번호> - 권한이 필요없는 페이지는 저장하지 않는다.
	//uri 를 넣으면 권한번호가 나온다
	Map<String, Integer> authorityMap = new HashMap<String, Integer>();
	
	/**
     * @see HttpFilter#HttpFilter()
     */
    public AuthorityFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		//캐스팅하기 쉽게 변수처리함
		HttpServletRequest req = (HttpServletRequest)request;
		
		//ServletRequest 에는 path 메서드가 없다 서버가 없어서 
		//캐스팅 해주면 나온다 HttpServletRequest로 캐스팅해준다
		String uri = req.getServletPath();
//		String query = req.getQueryString();
		
		System.out.println("AuthorityFilter.doFilter().uri" + uri);
		
		//uri 를 넣으면 권한번호가 나온다
		Integer pageAuthority = authorityMap.get(uri);
		
		//request안에 session을 집어넣어 놓는다(but http에 저장되어있는 메서드라 캐스팅 해줘야함)
		//로그인이 필요한 경우
		if(pageAuthority != null) {
			HttpSession session = req.getSession();
			//vo가 null 이면 로그인 하지 않았다 -> login 페이지로 이동시킨다.
			//object에서 바로 loginVO 로 갈수 없어서 캐스팅한다
			LoginVO loginVO = (LoginVO) session.getAttribute("login");
			
			//response.sendredirect 타입이 달라서 없음 
			//아이디가 없으면 로그인 창으로 보내고 return
			if(loginVO == null) {
				
				//uri+?+query -> session에 저장해 놓으면 로그인 후 가려는 페이지로 이동이 가능하다
				((HttpServletResponse)response).sendRedirect("/member/loginForm.do");
				//session에 저장
//				req.setAttribute("", uri+"?"+query);
				//더이상 처리되지않도록 return 시킨다
				return;
				
			}
			
			//관리자 페이지 권한 처리
			if(pageAuthority == 9 ) {
				//관리자가 아닌경우 (저장된 vo에서 등급번호 꺼내서 비교)
				if(loginVO.getGradeNo() < 9) {
					((HttpServletResponse)response).sendRedirect("/error/authorityError.do");
				return;
				}
				
			}
			
			
		}
		
		//서버없으면 uri 서버있으면 url
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 * 서버가 실행될때 추기화 시키기 위해 딱 한번만 실행되는 메서드(초기화메서드)
	 * uri의 페이지 권한 정보를 저장해 놓는다
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		//서버가 돌아가면서 실행시켜서 url 저장하는곳 
		System.out.println("AuthorityFilter.init()=============");
		
		//페이지와 권한 저장 add, put 둘중하나로 대부분 메서드가 잉ㅆ다 
		//이미지 권한
//		authorityMap.put("/image/writeForm.do", 1);
//		authorityMap.put("/image/write.do", 1);
//		authorityMap.put("/image/updateForm.do", 1);
//		authorityMap.put("/image/update.do", 1);
//		authorityMap.put("/image/delete.do", 1);
//		
//		
//		//공지사항 권한
//		authorityMap.put("/notice/writeForm.do", 9);
//		authorityMap.put("/notice/write.do", 9);
//		authorityMap.put("/notice/updateForm.do", 9);
//		authorityMap.put("/notice/update.do", 9);
//		authorityMap.put("/notice/delete.do", 9);
		
		//회원관리 권한
//		authorityMap.put("/member/list.do", 9);
		
	}

}
