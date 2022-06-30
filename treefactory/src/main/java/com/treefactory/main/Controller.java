package com.treefactory.main;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.RequestContext;

public interface Controller {

	//request를 전달받아 처리한다, 
	//Strirg 은 JSP 에 대한 정보(어떤JSP를 쓸것인지)를 담고있다, URL 이동 정보를 담고있다("redirect:url" 형식 사용)
	public String execute(HttpServletRequest request) throws Exception;

}
