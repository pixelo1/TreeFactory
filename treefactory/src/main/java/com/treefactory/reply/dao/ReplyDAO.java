package com.treefactory.reply.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.treefactory.reply.vo.ReplyVO;
import com.treefactory.util.ReplyPageObject;
import com.treefactory.util.db.DB;

public class ReplyDAO {
	

	
	// DB 처리를 위해 필요한 객체
	// 연결 객체
	Connection con = null;
	// 실행 객체
	PreparedStatement pstmt = null;
	// DB에서 가져온 데이터를 저장하는 객체
	ResultSet rs = null;
	
	//예외는web.xml에 처리해둠
	public Long getTotalRow(ReplyPageObject replyPageObject) throws Exception {
		Long result = 0L;
		
		try {
			//1 드라이버 확인
			con = DB.getConnection();
			//3 보고있는 글번호의 댓글 개수 가져오기
			String sql = "select count(*) from board_reply where no = ?";
			//4/
			pstmt = con.prepareStatement(sql);
			//PageObject 상속받아 no 만 추가시켜 클래스 만들어둠
			pstmt.setLong(1, replyPageObject.getNo());
			//5
			rs = pstmt.executeQuery();
			//6
			if(rs != null && rs.next()) {
				result = rs.getLong(1);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("댓글 개수 db 처리중 오류");
		}finally {
			
			try {
				
				if(con !=null)con.close();
				if(pstmt !=null)pstmt.close();
				if(rs !=null)rs.close();
				
			} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("댓글 개수 db객체 닫기 처리중 오류");
				
			}
		}
		
		return result;
		
	}

	public List<ReplyVO> list (ReplyPageObject replyPageObject) throws Exception{
		List<ReplyVO> list = null;

		
		try {
			//1 드라이버 확인
			con = DB.getConnection();
			//3 보고있는 글번호의 댓글 개수 가져오기
			String sql = " select rno, no, content, id, to_char(writeDate, 'yyyy-mm-dd') writeDate "
					+ " from board_reply "
					+ " where no = ? order by rno desc";
			
			sql = " select rownum rnum, rno, no, content, id, writeDate from ( " +sql+ " ) ";
			sql = " select rnum, rno, no, content, id, writeDate from ( " +sql+ " ) where rnum between ? and ? " ;

			//
//			String sql = " select br.rno, br.no, br.content, br.id, m.name, to_char(br.writeDate, 'yyyy-mm-dd') writeDate "
//					+ " from board_reply br, member m "
//					+ " where (br.no = ?) and (br.id = m.id) order by rno desc";
//			sql = " select rownum rnum, rno, no, content, id, name, writeDate from ( " +sql+ " ) ";
//			sql = " select rnum, rno, no, content, id, name, writeDate from ( " +sql+ " ) where rnum between ? and ? " ;
			//4/
			System.out.println("sql문 : "+sql);
			pstmt = con.prepareStatement(sql);
			//PageObject 상속받아 no 만 추가시켜 클래스 만들어둠
			pstmt.setLong(1, replyPageObject.getNo());
			pstmt.setLong(2, replyPageObject.getStartRow());
			pstmt.setLong(3, replyPageObject.getEndRow());
			//5
			rs = pstmt.executeQuery();
			//6
			if(rs != null) {
//				System.out.println("rs ");
				
//				System.out.println("rs.next "+rs.next());
				while(rs.next()) {
//					System.out.println("rs.next ");
					if(list == null) list = new ArrayList<ReplyVO>();
						ReplyVO vo = new ReplyVO();
						vo.setRno(rs.getLong("rno"));
						vo.setNo(rs.getLong("no"));
						vo.setContent(rs.getString("content"));
						vo.setId(rs.getString("id"));
//						vo.setName(rs.getString("name"));
						vo.setWriteDate(rs.getString("writeDate"));
						System.out.println("저장된 댓글 vo - " + vo);
						list.add(vo);
				}
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("댓글 리스트 db 처리중 오류");
		}finally {
			
			try {
				
				if(con !=null)con.close();
				if(pstmt !=null)pstmt.close();
				if(rs !=null)rs.close();
				
			} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("댓글 리스트 db객체 닫기 처리중 오류");
				
			}
		}
		
		System.out.println("저장된 댓글 - " + list);
		return list;
	}

	public Integer write(ReplyVO vo)throws Exception {

		Integer result = 0;
		try {
			//1 드라이버 확인
			con = DB.getConnection();
			//3 보고있는 글번호의 댓글 개수 가져오기
			String sql = " insert into board_reply (rno, no, content, id) "
					+ " values (board_reply_seq.nextval, ?, ?, ? ) ";
			//4/
			pstmt = con.prepareStatement(sql);
			//PageObject 상속받아 no 만 추가시켜 클래스 만들어둠
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getId());
			//5
			result = pstmt.executeUpdate();
			//6
			System.out.println("ReplyDAO.write() - 댓글 등록이 되었습니다");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("댓글 등록 db 처리중 오류");
		}finally {
			
			try {
				
				if(con !=null)con.close();
				if(pstmt !=null)pstmt.close();
				
			} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("댓글 등록 db객체 닫기 처리중 오류");
				
			}
		}

		
		return result;
	}

	
	public Integer update(ReplyVO vo) throws Exception {

		Integer result = 0;
		try {
			//1 드라이버 확인
			con = DB.getConnection();
			//3 보고있는 글번호의 댓글 개수 가져오기
			String sql = " update board_reply set content = ? "
					+ " where rno = ? ";
			//4/
			pstmt = con.prepareStatement(sql);
			//PageObject 상속받아 no 만 추가시켜 클래스 만들어둠
			pstmt.setString(1, vo.getContent());
			pstmt.setLong(2, vo.getRno());
			//5
			result = pstmt.executeUpdate();
			//6
			System.out.println("ReplyDAO.update() - 댓글 수정이 되었습니다");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("댓글 수정 db 처리중 오류");
		}finally {
			
			try {
				
				if(con !=null)con.close();
				if(pstmt !=null)pstmt.close();
				
			} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("댓글 수정 db객체 닫기 처리중 오류");
				
			}
		}

		
		return result;
	}

	public Integer delete(ReplyVO vo) throws Exception {

		Integer result = 0;
		try {
			//1 드라이버 확인
			con = DB.getConnection();
			//3 보고있는 글번호의 댓글 개수 가져오기
			String sql = " delete from board_reply where rno = ? and id = ? ";
			//4/
			pstmt = con.prepareStatement(sql);
			//PageObject 상속받아 no 만 추가시켜 클래스 만들어둠
			pstmt.setLong(1, vo.getRno());
			pstmt.setString(2, vo.getId());
			//5
			result = pstmt.executeUpdate();
			//6
			System.out.println("ReplyDAO.delete() - 댓글이 삭제 되었습니다");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("댓글 삭제 db 처리중 오류");
		}finally {
			
			try {
				
				if(con !=null)con.close();
				if(pstmt !=null)pstmt.close();
				
			} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("댓글 삭제 db객체 닫기 처리중 오류");
				
			}
		}

		
		return result;
	}
}
