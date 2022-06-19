package com.treefactory.main;

public class Execute {

	
	//service를 찾아서 실행한후 결과를 돌려 받는 메서드 만들기
	//리턴타입 인터페이스와 맞춘다
	//service( uri, 서비스쪽으로 넘어가는 수집한 데이터(사용자가 쓴글, 글번호...))
	// 이미 service를 전달받은 상태 service 에 직접 listservice 값 처럼 컨트롤러에서 넣어줄것
	public static Object service(Service service, Object obj) throws Exception {
		
		//service 꺼내오기 servlet에서 메서드 실행 - uri 넣으면 new로 객체 생성
		//uri 에 해당하는 service 객체가 생성된다
		//시작시간
		long start = System.currentTimeMillis();
		System.out.println("시작시간");
		
		//uri 에 해당하는 이미만들어진 서비스 주소를 가져온다ex)BoardListService
		System.out.println(" ");
		System.out.println("Execute.service()를 이용해서 서비스 실행 ------");
		System.out.println("----------------------------------------");
		System.out.println("실행되는 클래스 이름 : " + service.getClass().getName());
		System.out.println("넘어가는 데이터: " + obj);
		
		//실행한다 가져온 서비스 클래스 객체를 실행하고 Object로 받아둔다
		Object result = service.service(obj);
		
		long end = System.currentTimeMillis();
		System.out.println("- 처리된 결과 : " + result);
		System.out.println(" 처리시간 :  " + (end - start));
		System.out.println("----------------------------------------");
		return result;
	}
	
}
