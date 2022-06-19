package com.treefactory.util.io;

import java.util.Scanner;

// 접근제한자 - 보통 public. class - 클래스. 클래스이름(대문자로 시작)
// 클래스 - 초기화블록, 생성자, 변수, 메서드
// In(input) - 키보드로 입력을 담당하는 클래스 -> Scanner 관련된 변수. 메서드
public class In {
	
	// 변수 선언 = 초기값 : 명시적 초기화 - 접근제한자와 그외 제한자
	// 외부에서 접근 안됨.
	private static Scanner scanner = new Scanner(System.in);
	
	// 문자열을 키보드로 입력받는 메서드
	// In.getString(); - 키보드로 입력받는 메서드를 호출해서 입력한다.
	public static String getString() {
		return scanner.nextLine();
	}
	
	// 안내를 출력하고 입력받는다.
	// In.getString("메뉴입력")
	public static String getString(String msg) {
		System.out.print(msg + " -> ");
		return getString();
	}
	
	// int 숫자를 입력 처리하는 메서드
	public static int getInt(String msg) {
		while(true) {
			try {
				// 문자열을 입력 받는다.
				String str = getString(msg);
				return Integer.parseInt(str);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("숫자를 입력하셔야 합니다.");
			}
		}
	}

	// long 숫자를 입력 처리하는 메서드
	public static long getLong(String msg) {
		while(true) {
			try {
				// 문자열을 입력 받는다.
				String str = getString(msg);
				return Long.parseLong(str);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("숫자를 입력하셔야 합니다.");
			}
		}
	}
	
}
