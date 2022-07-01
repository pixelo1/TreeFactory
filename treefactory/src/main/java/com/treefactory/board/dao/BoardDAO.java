package com.treefactory.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.treefactory.board.vo.BoardFileUploadVO;
import com.treefactory.board.vo.BoardVO;
import com.treefactory.util.CategoryPageObject;
import com.treefactory.util.db.DB;
import com.webjjang.util.PageObject;

// 게시판 데이터베이스 처리를 하는 객체 - Controller - Service - [DAO]
public class BoardDAO {

	// DB 연결 정보
	
	// DB 처리를 위해 필요한 객체
	// 연결 객체
	Connection con = null;
	// 실행 객체
	PreparedStatement pstmt = null;
	// DB에서 가져온 데이터를 저장하는 객체
	ResultSet rs = null;
	
	// 게시판 리스트
	public List<BoardVO> list(CategoryPageObject categoryPageObject) throws Exception {
		
		// 리턴 파입과 동일한 변수 - 데이터가 있다면 데이터를 채워서 리턴시킨다.
		List<BoardVO> list = null;
		
//		System.out.println("BoardDAO.list().pageObject : "+ pageObject +"-----");
		
		try {
			
			// 1. 드라이버 확인 - static 요소들을 모두 로딩한다.
			con = DB.getConnection();
//			System.out.println("DB 연결 완료");
			
			// 3. 실행 쿼리 작성
			//  - list 데이터를 여러개의 데이터를 가져온다. - 최근 글이 맨 앞에 보이도록 가져온다.
			//1) 원본 데이터 가져오기
			String sql = "select no, title, id, to_char(writeDate, 'yyyy-mm-dd') writeDate, hit, rec, category "
					+ " from board ";
			
			//검색어가 있는 경우 검색을 붙인다
			sql += getSearchCondition(categoryPageObject);
			sql += getCategoryCondition(categoryPageObject);
			
				sql += " order by no desc ";
			//2)순서번호를 붙인다.
			sql = " select rownum rnum, no, title, id, writeDate, hit, rec, category from( " + sql +" ) ";
			//3) 1페이지에 해당되는 데이터 가져오기
			sql = " select rnum, no, title, id, writeDate, hit, rec, category from (" + sql + " ) "
					+ " where rnum between ? and ? ";
			
			System.out.println("sql 문 : "+sql);
			// 4. 실행객체 & 데이터 세팅
			int idx = 0;
			pstmt = con.prepareStatement(sql);
			// 검색어가 있는 경우 검색 데이터를 붙인다.
			idx = setData(categoryPageObject, pstmt, idx);
			
			pstmt.setLong(++idx, categoryPageObject.getStartRow());
			pstmt.setLong(++idx, categoryPageObject.getEndRow());
//			System.out.println("실행 객체 생성 완료");
			
			// 5. 실행
			rs = pstmt.executeQuery();
//			System.out.println("DB 쿼리 실행 완료");
			
			// 6. 데이터 담기 - 만약(if)에 rs가 null이 아니고 데이터를 있는(rs.next()) 만큼 반복처리(while()) 해서 list에 담는다.
			if(rs != null) {
				// 데이터가 있는 만큼 반복처리
				while(rs.next()) {
					// list가 null이면 클래스 생성해준다. List 인터페이스이므로 상속받은 클래스를 생성해서 넣어 주셔야 한다.
					if(list == null) list = new ArrayList<BoardVO>();
					BoardVO vo = new BoardVO();
					// rs -> vo에 저장
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setId(rs.getString("id"));
					vo.setWriteDate(rs.getString("writeDate"));
					vo.setHit(rs.getLong("hit"));
					vo.setRec(rs.getLong("rec"));
					vo.setCategory(rs.getString("category"));
					
					// vo -> list에 저장
					list.add(vo);
				} // end of while
			} // end of if
			
		} catch (Exception e) {
			// TODO: handle exception
			// 개발자를 위한 코드
			e.printStackTrace();
			// 메시지를 담은 예외 생성해서 다시 던진다.
			throw new Exception("게시판 리스트 데이터를 가져오는 중 오류 발생. - " + e.getMessage());
			// 만약에 발생된 예외를 그대로 넘기려고 한다면 throw new Exception(e);
		} finally {
			// 예외처리를 한다.
			try {
				// 7.사용한 객체를 닫는다.
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				// 개발자를 위한 코드
				e.printStackTrace();
				// 메시지를 담은 예외 생성해서 다시 던진다.
				throw new Exception("게시판 리스트 데이터를 처리객체 닫는 중 오류 발생. - " + e.getMessage());
			}
		}
		
		// list에 담겨 있는 데이터 확인
//		System.out.println(list);
		
		return list;
		
	}
	
	//검색 조건 문장을 만들어 내는 메서드 - PageObject.word에 따라 달라짐
	//sql-> str 로 바꿈
	public String getSearchCondition(CategoryPageObject categoryPageObject) {
		
		String str = " where ( 1=0";
		
		if(categoryPageObject.getWord() != null && !categoryPageObject.getWord().equals("")) {
			if(categoryPageObject.getKey().indexOf("t") >= 0)
				str += " or title like ? ";
			if(categoryPageObject.getKey().indexOf("c") >= 0)
				str += " or content like ? ";
			if(categoryPageObject.getKey().indexOf("i") >= 0)
				str += " or id like ? ";
		}
		else {
			str += " or 1=1 ";
		}
		
		str += " )";
		return str;
	
	}
	public String getCategoryCondition(CategoryPageObject categoryPageObject) throws Exception {
		
		String str = " and ( 1=1";
		System.out.println("카테고리" + categoryPageObject.getCategory());
		switch (categoryPageObject.getCategory()) {
		case "정기모임/스터디":
			str += " and category = '정기모임/스터디' ";
			break;
		case "포럼":
			str += " and category = '포럼' ";
			break;

		case "all":

			break;
			
		default:
			throw new Exception("잘못된 카테고리 데이터 입니다.");
		}
		
		str += " )";
		return str;
		
	}
	
	
	//검색 데이터를 세팅하는 메서드
	//pageObject,pstmt 는 주소값이라 리턴 안해줘도 값이 저장되어 있는것이 연결된다
	public int setData(CategoryPageObject categoryPageObject, PreparedStatement pstmt, int idx) throws Exception {

		//검색어가 있으면 데이터 세팅
		if(categoryPageObject.getWord() != null && !categoryPageObject.getWord().equals("")) {
			if(categoryPageObject.getKey().indexOf("t") >= 0)
				pstmt.setString(++idx, "%" + categoryPageObject.getWord() + "%");
			if(categoryPageObject.getKey().indexOf("c") >= 0)
				pstmt.setString(++idx, "%" + categoryPageObject.getWord() + "%");
			if(categoryPageObject.getKey().indexOf("i") >= 0)
				pstmt.setString(++idx, "%" + categoryPageObject.getWord() + "%");
		}
		
		return idx;
	}
	
	
	
	public Long getTotalRow(CategoryPageObject categoryPageObject) throws Exception{
		// 리턴 타입과 동일한 변수 - 데이터가 있다면 데이터를 채워서 리턴시킨다.
		Long totalRow = 0L;
		
//		System.out.println("BoardDAO.getTotalRow().pageObject : " + pageObject + " --------------------");
		
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
//			System.out.println("DB 연결 완료");
			// 3. sql 작성 - 변경되는 데이터는 ? (대체문자)로 작성
			String sql = " SELECT count(*) FROM board  ";
			
			sql += getSearchCondition(categoryPageObject);
			sql += getCategoryCondition(categoryPageObject);
			// 4. 실행 객체 & 데이터세팅 - no
			System.out.println("sql :" +sql);
			//조건이 있으면 데이터를 세팅한다
			//뒤에 내용이 더있다면
			int idx = 0;
			pstmt = con.prepareStatement(sql);
			idx = setData(categoryPageObject, pstmt, idx);
			
//			setData(pageObject, pstmt, 0);
			
			//   데이터 타입에 따른 메서드를 선택해서 세팅해준다.
			// 5. 실행 
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			rs = pstmt.executeQuery();
//			System.out.println("DB 쿼리 실행 완료");
			// 6. 데이터 표시나 데이터 담기 - 만약(if)에 rs가 null이 아니고 데이터가 있는 경우(if) 데이터 가져온다.
			if(rs != null && rs.next()) {
				// 위에서 선언한 vo 객체를 생성하고 데이터를 담는다.
				//첫번째 순서번호인 1 사용가능 쿼리에 alis 지정해서 사용도 가능
				totalRow = rs.getLong(1);
			}
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			// Controller에서 예외처리를 시키기 위해서 예외를 생성하고 던진다.
			throw new Exception("게시판 전체 글 개수 DB 처리 중 오류 발생 - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				// Controller에서 예외처리를 시키기 위해서 예외를 생성하고 던진다.
				throw new Exception("게시판 전체 글 개수 DB 자원 닫기 중 오류 발생 - " + e.getMessage());
			}
		}
		
		// 데이터 확인
//		System.out.println(totalRow);
		System.out.println("totalRow 출력 ="+totalRow);
		return totalRow;
	}

		
	
	public BoardVO view(long no) throws Exception{
		// 리턴 타입과 동일한 변수 - 데이터가 있다면 데이터를 채워서 리턴시킨다.
		BoardVO vo = null;
		
		System.out.println("BoardDAO.view().no : " + no + " --------------------");
		
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
//			System.out.println("DB 연결 완료");
			// 3. sql 작성 - 변경되는 데이터는 ? (대체문자)로 작성
			String sql = " select id,to_char(writeDate, 'yyyy-mm-dd') writeDate, updateDate, no, category, title, content, fileName, rec, hit "
					+ " FROM board WHERE no = ?";
			// 4. 실행 객체 & 데이터세팅 - no
			pstmt = con.prepareStatement(sql);
			//   데이터 타입에 따른 메서드를 선택해서 세팅해준다.
			pstmt.setLong(1, no);
//			System.out.println("실행 객체 생성 완료");
			// 5. 실행 
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			rs = pstmt.executeQuery();
//			System.out.println("DB 쿼리 실행 완료");
			// 6. 데이터 표시나 데이터 담기 - 만약(if)에 rs가 null이 아니고 데이터가 있는 경우(if) 데이터 가져온다.
			if(rs != null && rs.next()) {
				// 위에서 선언한 vo 객체를 생성하고 데이터를 담는다.
				vo = new BoardVO();
				vo.setId(rs.getString("id"));
				vo.setWriteDate(rs.getString("writeDate"));
				vo.setUpdateDate(rs.getString("updateDate"));
				vo.setNo(rs.getLong("no"));
				vo.setCategory(rs.getString("category"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setFileName(rs.getString("fileName"));
				vo.setRec(rs.getLong("rec"));
				vo.setHit(rs.getLong("hit"));
				
			}
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			// Controller에서 예외처리를 시키기 위해서 예외를 생성하고 던진다.
			throw new Exception("게시판 글보기 DB 처리 중 오류 발생 - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				// Controller에서 예외처리를 시키기 위해서 예외를 생성하고 던진다.
				throw new Exception("게시판 글보기 DB 자원 닫기 중 오류 발생 - " + e.getMessage());
			}
		}
		
		// 데이터 확인
		System.out.println(vo);
		
		return vo;
	}

	
	public List<BoardFileUploadVO> viewUploadFile(long no) throws Exception{
		// 리턴 타입과 동일한 변수 - 데이터가 있다면 데이터를 채워서 리턴시킨다.
		List<BoardFileUploadVO> listBoardFileUploadVO = null;
		BoardFileUploadVO vo = null;
		
		System.out.println("BoardDAO.view().no : " + no + " --------------------");
		
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
//			System.out.println("DB 연결 완료");
			// 3. sql 작성 - 변경되는 데이터는 ? (대체문자)로 작성
			String sql = " select orgFileName, fileName, fileSize from board_fileUpload where boardNo = ? ";
			// 4. 실행 객체 & 데이터세팅 - no
			pstmt = con.prepareStatement(sql);
			//   데이터 타입에 따른 메서드를 선택해서 세팅해준다.
			pstmt.setLong(1, no);
//			System.out.println("실행 객체 생성 완료");
			// 5. 실행 
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			rs = pstmt.executeQuery();
//			System.out.println("DB 쿼리 실행 완료");
			// 6. 데이터 표시나 데이터 담기 - 만약(if)에 rs가 null이 아니고 데이터가 있는 경우(if) 데이터 가져온다.
			if(rs != null) {

				while(rs.next()) {
					if(listBoardFileUploadVO==null)listBoardFileUploadVO = new ArrayList<BoardFileUploadVO>();
				vo = new BoardFileUploadVO();
				vo.setOrgFileName(rs.getString("orgFileName"));
				vo.setFileName(rs.getString("fileName"));
				vo.setFileSize(rs.getLong("fileSize"));
//				vo.setRealSavePath(rs.getString("realSavePath"));
				
				listBoardFileUploadVO.add(vo);
				}
			}
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			// Controller에서 예외처리를 시키기 위해서 예외를 생성하고 던진다.
			throw new Exception("게시판 이미지view DB 처리 중 오류 발생 - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				// Controller에서 예외처리를 시키기 위해서 예외를 생성하고 던진다.
				throw new Exception("게시판 이미지view DB 자원 닫기 중 오류 발생 - " + e.getMessage());
			}
		}
		
		// 데이터 확인
		System.out.println(vo);
		
		return listBoardFileUploadVO;
	}
	
	
	public Integer increase(long no) throws Exception{
		// return 타입과 동일한변수 선언
		Integer result = 0;
		
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
//			System.out.println("연결 완료");
			// 3. SQL 작성
			String sql = "UPDATE board SET hit = hit + 1 WHERE no = ?";
			// 4. 실행객체 & 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			// 5. 실행
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			if(result >= 1)
				System.out.println("게시판 조회수 1증가 DB 처리 완료");
			else
				System.out.println("게시판 조회수 1증가 DB 처리가 되지 않았습니다. 글번호를 확인해 주세요.");
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("게시판 조회수 1증가 DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("게시판 조회수 1증가 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return result;
	}

	// 게시판 글수정
	// DB 쿼리 : UPDATE board SET title = ?, content = ?, writer = ? WHERE no = ?
	// 번호, 제목, 내용, 작성자 -> BoardVO 받아서 처리한다. - BoardVO vo
	// 데이터가 수정이 됐으면 1이 안됐으면 0이 리턴된다.
	public Integer update(BoardVO vo) throws Exception{
		// return 타입과 동일한변수 선언
		Integer result = 0;
		
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
//			System.out.println("연결 완료");
			// 3. SQL 작성
			String sql = " UPDATE board SET title = ?, content = ?, id = ? ";
				
			if(vo.getFileName() != null)sql += " , fileName = ? ";

					
				sql	+= " WHERE no = ?";
			// 4. 실행객체 & 데이터 세팅
				int idx = 0;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(++idx, vo.getTitle());
			pstmt.setString(++idx, vo.getContent());
			pstmt.setString(++idx, vo.getId());
			if(vo.getFileName() != null)
				pstmt.setString(++idx, vo.getFileName());
			pstmt.setLong(++idx, vo.getNo());
			// 5. 실행
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			if(result >= 1)
				System.out.println("게시판 수정 DB 처리 완료");
			else
				System.out.println("게시판 수정 DB 처리가 되지 않았습니다. 글번호를 확인해 주세요.");
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("게시판 글수정 DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("게시판 글수정 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return result;
	}

	// 게시판 글쓰기
	// DB 쿼리 : INSERT INTO board(no, title, content, writer) VALUES (board_seq.NEXTVAL, ?, ?, ?);
	// 제목, 내용, 작성자 -> BoardVO 받아서 처리한다. - BoardVO vo
	public Integer write(BoardVO vo) throws Exception{
		// return 타입과 동일한변수 선언
		Integer result = 0;
		
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
			//System.out.println("연결 완료");
			// 3. SQL 작성
			String sql = "INSERT INTO board(no, title, content, id, fileName) VALUES (board_seq.NEXTVAL, ?, ?, ?, ?)";
			
			// 4. 실행객체 & 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getId());
			pstmt.setString(4, vo.getFileName());

			
			System.out.println("BoardDAO.write().service" +sql);
			
			// 5. 실행
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			//System.out.println("게시판 등록 DB 처리 완료");
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("게시판 글등록 DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("게시판 글등록 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return result;
	}

	// 스키마 나눠서 운용해서 안씀
	public Integer writeMulti(List<BoardVO> listBoardVO) throws Exception{
		// return 타입과 동일한변수 선언
		Integer result = 0;
		
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
			//System.out.println("연결 완료");
			// 3. SQL 작성
			String sql = "INSERT INTO board(no, title, content, id, fileName) VALUES (board_seq.NEXTVAL, ?, ?, ?, ?)";
			for(BoardVO vo : listBoardVO) {
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setString(3, vo.getId());
				pstmt.setString(4, vo.getFileName());
				
				result += pstmt.executeUpdate();
			}
			
//			String sql = "INSERT INTO board(no, title, content, id, fileName) VALUES (board_seq.NEXTVAL, ?, ?, ?, ?)";
//			
//			// 4. 실행객체 & 데이터 세팅
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, vo.getTitle());
//			pstmt.setString(2, vo.getContent());
//			pstmt.setString(3, vo.getId());
//			pstmt.setString(4, vo.getFileName());
//			
			
			System.out.println("BoardDAO.write().service" +sql);
			System.out.println("BoardDAO.write().service result : " +result);
			
			// 5. 실행
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
//			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			//System.out.println("게시판 등록 DB 처리 완료");
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("게시판 글등록 DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("게시판 글등록 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return result;
	}

	
	
	public BoardVO writeNoFile(BoardVO vo) throws Exception{
		// return 타입과 동일한변수 선언
		Integer result = 0;
		BoardVO boardVO = new BoardVO();
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
			//System.out.println("연결 완료");
			// 3. SQL 작성
			String sql = "INSERT INTO board(no, title, content, id) VALUES (board_seq.NEXTVAL, ?, ?, ?)";
			
			// 4. 실행객체 & 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getId());

			
			System.out.println("BoardDAO.write().service" +sql);
			
			// 5. 실행
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			result = pstmt.executeUpdate();
			
			if (result != 0) {
				sql = new String();
				sql = " select max(no) from board where id = ? ";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getId());
				
				rs = pstmt.executeQuery();
				
				if(rs != null && rs.next()) {
					boardVO.setNo(rs.getLong(1));
				}
				
				
			}
			// 6. 표시 또는 담기
			//System.out.println("게시판 등록 DB 처리 완료");
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("게시판 글등록(nofile) DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("게시판 글등록(nofile) 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return boardVO;
	}

	
	public Long getMaxNo(String id) throws Exception{
		// return 타입과 동일한변수 선언
		Long maxNo = 0L;
		
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
			//System.out.println("연결 완료");
			// 3. SQL 작성
			
				String sql = " select max(no) from board where id = ? ";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				
				if(rs !=null || rs.next()) {
					maxNo =rs.getLong(1);
				}
				
				
			// 6. 표시 또는 담기
			//System.out.println("게시판 등록 DB 처리 완료");
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("게시판 글등록(nofile) DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("게시판 글등록(nofile) 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return maxNo;
	}
	

	
	
	// 제목, 내용, 작성자 -> BoardVO 받아서 처리한다. - BoardVO vo
	public Integer writeMultiUpload(List<BoardFileUploadVO> listBoardFileUploadVO, Long no) throws Exception{
		// return 타입과 동일한변수 선언
		Integer result = 0;
		
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
			//System.out.println("연결 완료");
			// 3. SQL 작성
			String sql = "INSERT INTO board_fileUpload(uploadNo, boardNo, orgFileName, fileName, fileSize, realSavePath) "
					+ " VALUES (board_fileUpload_seq.NEXTVAL, ?, ?, ?, ?, ?)";
//			String sql = "INSERT INTO board(no, title, content, id, fileName) VALUES (board_seq.NEXTVAL, ?, ?, ?, ?)";
			for(BoardFileUploadVO vo : listBoardFileUploadVO) {
				if(vo != null && !vo.equals(""))
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, no);
				
				pstmt.setString(2, vo.getOrgFileName());
				pstmt.setString(3, vo.getFileName());
				pstmt.setLong(4, vo.getFileSize());
				pstmt.setString(5, vo.getRealSavePath());
				
				result += pstmt.executeUpdate();
			}
			
			System.out.println("BoardDAO.write().service" +sql);
			System.out.println("BoardDAO.write().service result : " +result);
			
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("게시판 글등록 DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("게시판 글등록 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return result;
	}


	public Integer delete(long no) throws Exception{
		// return 타입과 동일한변수 선언
		Integer result = 0;
		
		System.out.println("BoardDAO.delete().no : " +no);
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
//			System.out.println("연결 완료");
			// 3. SQL 작성
			String sql = "DELETE FROM board WHERE no = ?";
			// 4. 실행객체 & 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			// 5. 실행
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			if(result >= 1)
				System.out.println("게시판 삭제 DB 처리 완료");
			else
				System.out.println("게시판 삭제 DB 처리가 되지 않았습니다. 글번호를 확인해 주세요.");
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("게시판 글삭제 DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("게시판 글삭제 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return result;
	}

	// 데이터가 삭제가 됐으면 1이 안됐으면 0이 리턴된다.
	public Integer deleteUploadFile(BoardFileUploadVO boardFileUploadVO) throws Exception{
		// return 타입과 동일한변수 선언
		Integer result = 0;
		
		System.out.println("BoardDAO.deleteUploadFile().boardFileUploadVO : " +boardFileUploadVO);
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
//			System.out.println("연결 완료");
			// 3. SQL 작성
			String sql = "DELETE FROM board_fileUpload WHERE boardNo = ? and realSavePath = ?";
			// 4. 실행객체 & 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardFileUploadVO.getBoardNo());
			pstmt.setString(2, boardFileUploadVO.getRealSavePath());
			// 5. 실행
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			if(result >= 1)
				System.out.println("게시판 파일 DB 처리 완료");
			else
				System.out.println("게시판 파일 DB 처리가 되지 않았습니다. 글번호를 확인해 주세요.");
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("게시판 파일삭제 DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("게시판 파일삭제 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return result;
	}
	
	// 데이터가 삭제가 됐으면 1이 안됐으면 0이 리턴된다.
	public Integer deleteAllFile(Long boardNo) throws Exception{
		// return 타입과 동일한변수 선언
		Integer result = 0;
		
		// 데이터처리
		try {
			// 1. 드라이버 확인
			con = DB.getConnection();
//			System.out.println("연결 완료");
			// 3. SQL 작성
			String sql = "DELETE FROM board_fileUpload WHERE boardNo = ?";
			// 4. 실행객체 & 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			// 5. 실행
			// - select처리 : executeQuery() - rs가 나온다. insert,update,delete 처리 : executeUpdate() - int가 나온다.
			result = pstmt.executeUpdate();
			// 6. 표시 또는 담기
			if(result >= 1)
				System.out.println("해당게시판 모든파일 DB 처리 완료");
			else
				System.out.println("해당게시판 모든파일 DB 처리가 되지 않았습니다. 글번호를 확인해 주세요.");
		} catch (Exception e) {
			// 개발자를 위한 코드
			e.printStackTrace();
			throw new Exception("해당게시판 모든파일 DB 처리 중 오류. - " + e.getMessage());
		} finally {
			try {
				// 7. 닫기
				//  - commit 까지 완료하고 나온다. - auto commit
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				// 개발자를 위한 코드
				e.printStackTrace();
				throw new Exception("해당게시판 모든파일 객체를 닫는 중 오류. - " + e.getMessage());
			}
		}
		
		return result;
	}
	

}
