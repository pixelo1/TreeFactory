package com.treefactory.util.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 *EncodingFilter 라고 쓰면 dofilter 찾아간다 - web.xml에 지정할거라 지워준다
 */
public class EncodingFilter extends HttpFilter implements Filter {
       
    /**
	 * 버전 표시 해줘야 경고 없어진다
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public EncodingFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 * 없어질때 처리 메서드
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * 필터 처리를 위해 자동으로 실행되는 메서드
	 * http를뺀 서블릿리퀘스트만 파라미터로 받아왔다(따로 메서드를 만들어서 가져온것)
	 * ServletRequest 은 url 처리 안해서 httpServletRequest를 만들어서 쓴것이다
	 * ServletRequest 를 상속 받아 http~~ 만들었다 
	 * 원래 request jsp에서 쓰면httpServletRequest 를 가져와서 씀
	 * 
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here 
		//실행 전처리(한글처리,권한처리...)
		request.setCharacterEncoding("utf-8");

		// pass the request along the filter chain
		//다음에 실행할 필터 객체를 받아서 실행해 준다. 결국 맨뒤에 처리하려는 프로그램이 있다
		
		
		chain.doFilter(request, response);
		//실행 후처리(web.xml 의 sitemesh처리)
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
