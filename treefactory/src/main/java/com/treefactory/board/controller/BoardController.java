package com.treefactory.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.treefactory.board.service.BoardDeleteService;
import com.treefactory.board.service.BoardFileUploadService;
import com.treefactory.board.service.BoardListService;
import com.treefactory.board.service.BoardUpdateService;
import com.treefactory.board.service.BoardViewService;
import com.treefactory.board.service.BoardWriteService;
import com.treefactory.board.service.BoardWriteService2;
import com.treefactory.board.service.BoardWriteService3;
import com.treefactory.board.vo.BoardFileUploadVO;
import com.treefactory.board.vo.BoardVO;
import com.treefactory.main.Controller;
import com.treefactory.main.Execute;
import com.treefactory.reply.service.ReplyListService;
import com.treefactory.reply.vo.ReplyVO;
import com.treefactory.util.CategoryPageObject;
import com.treefactory.util.ReplyPageObject;
import com.webjjang.util.PageObject;

public class BoardController implements Controller {

	//서버가 시작이 될 때 객체가 초기화 되어야만 한다 - DispacherServlet.init() 처리
	private BoardListService boardListService;
	private BoardViewService boardViewService;
	private BoardWriteService boardWriteService;
	private BoardUpdateService boardUpdateService;
	private BoardDeleteService boardDeleteService;
	private BoardWriteService2 boardWriteService2;
	private BoardWriteService3 boardWriteService3;
	private BoardFileUploadService boardFileUploadService;
	//댓글
	private ReplyListService replyListService;
	
	public void setBoardListService(BoardListService boardListService) {
		this.boardListService = boardListService;
	}

	public void setBoardViewService(BoardViewService boardViewService) {
		this.boardViewService = boardViewService;
	}

	public void setBoardWriteService(BoardWriteService boardWriteService) {
		this.boardWriteService = boardWriteService;
	}

	public void setBoardUpdateService(BoardUpdateService boardUpdateService) {
		this.boardUpdateService = boardUpdateService;
	}

	public void setBoardDeleteService(BoardDeleteService boardDeleteService) {
		this.boardDeleteService = boardDeleteService;
	}

	public void setReplyListService(ReplyListService replyListService) {
		this.replyListService = replyListService;
	}
	
	
	public void setBoardWriteService2(BoardWriteService2 boardWriteService2) {
		this.boardWriteService2 = boardWriteService2;
	}
	

	//request를 전달받아 처리한다, 
	//Strirg 은 JSP 에 대한 정보(어떤JSP를 쓸것인지)를 담고있다, URL 이동 정보를 담고있다("redirect:url" 형식 사용)
	public void setBoardWriteService3(BoardWriteService3 boardWriteService3) {
		this.boardWriteService3 = boardWriteService3;
	}

	
	
	public void setBoardFileUploadService(BoardFileUploadService boardFileUploadService) {
		this.boardFileUploadService = boardFileUploadService;
	}

	@Override
	public String execute(HttpServletRequest request) throws Exception{
		
		System.out.println("BoardController.execute()- 게싶판 처리하고 있다");
		
		String jsp = null;
		
		//uri - /board/실행서비스.do  - 처리 service 결정하는 - /list.do
		String uri = request.getServletPath();
		
		//두번째 슬레시부터 글자 찾기 - switch 문으로 해당되는것 실행되게끔 가져오는 처리
		String serviceUri = uri.substring(uri.indexOf("/", 1));
		System.out.println("BoardController.execute().serviceUri - "+serviceUri);
		
		switch (serviceUri) {
		//일반게시판 리스트
		case "/list.do":
			//page정보 받기
			CategoryPageObject categoryPageObject =  CategoryPageObject.getInstance(request);
			//jsp 자바단에 쓰던 실행 메서드 (excute 메서드 를 사용해도 되지만 spring에서 약간 달라져서 보류)
			
			String strCategory = request.getParameter("category");
			if(strCategory == null || strCategory.equals("")) {
				categoryPageObject.setCategory("all");
			}
			else{categoryPageObject.setCategory(strCategory);
			}
			
			request.setAttribute("list", Execute.service(boardListService, categoryPageObject));
			request.setAttribute("categoryPageObject", categoryPageObject);
			
			//redirect: -> url 이동 , 없으면 jsp 로 이동
			//url 에 추가로 값이 안넘어가도 되니까 jsp 로 보낸듯?
			jsp = "board/list";
			break;
			//일반게시판 글쓰기 폼
		case "/writeForm.do":
			
			jsp = "board/writeForm";
			break;
		case "/writeForm2.do":
			
			jsp = "board/writeForm2";
			break;
			//일반 게시판 글쓰기 처리
		case "/write.do":
			
			//주소창에 넘어오는 데이터받기
			String savePath = "/upload/image/";
			
			String realSavePath = request.getServletContext().getRealPath(savePath);
			
			File folder = new File(realSavePath);
			boolean isExist = folder.exists();
			
			if(!isExist) folder.mkdirs();
			
			System.out.println("realSavePath: " +realSavePath+", 존재 여부 :" + isExist);
			int maxSize = 1024 * 1024 * 20;
			
			MultipartRequest multi = new MultipartRequest(request, realSavePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
			String title = multi.getParameter("title");
			String content = multi.getParameter("content");
			String id = multi.getParameter("writer");
			
			String fileName = savePath+multi.getFilesystemName("image");
			
			String strPerPageNum = multi.getParameter("perPageNum");
			System.out.println("파일 이름 : " +fileName);
			//넘겨받은 데이터를 vo로 생성해서 넣어준다.
			BoardVO vo = new BoardVO();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setId(id);
			vo.setFileName(fileName);
			
			//db등록 / 이미 등록되어있는 service 가져와서 쓴다
			Execute.service(boardWriteService, vo);
			//이동
//			response.sendRedirect("list.jsp?perPageNum"+strPerPageNum);
			//redirect: - url 이동 , 없으면 jsp 로 이동
			jsp = "redirect:list.do?perPageNum="+strPerPageNum;
			break;

		case "/write2.do":
			
			
			savePath = "/upload/image2/";
			
			realSavePath = request.getServletContext().getRealPath(savePath);
			
			folder = new File(realSavePath);
			isExist = folder.exists();
			
			if(!isExist) folder.mkdirs();
			
			System.out.println("realSavePath: " +realSavePath+", 존재 여부 :" + isExist);
			maxSize = 1024 * 1024 * 20;
			// ' DiskFileItemFactory '는 업로드 된 파일을 저장할 저장소와 관련된 클래스
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			diskFileItemFactory.setDefaultCharset("utf-8");
			diskFileItemFactory.setRepository(new File(realSavePath));
			diskFileItemFactory.setSizeThreshold(maxSize);
			
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
			
			List<FileItem> items = fileUpload.parseRequest(request);
			
			BoardVO boardVO = new BoardVO();
			List<BoardFileUploadVO> listBoardFileUploadVO = new ArrayList<BoardFileUploadVO>();
			
			BoardFileUploadVO boardFileUploadVO = new BoardFileUploadVO();
			title = null;
			content = null;
			id = null;
			String separator = File.separator;
			fileName = null;
			strPerPageNum = null;
			for(FileItem item : items) {
				if(item.isFormField()) {
					//%s (문자열 형식)/ 업로드된 파일이 단순 text데이터면 실행
					System.out.println(String.format("<파일형식이 아닌 파라미터>파라미터명: %s, 파일 명: %s, 파일크기: %s bytes", item.getFieldName(), item.getString(), item.getSize()));
					if(item.getFieldName() == "title" || (item.getFieldName()).equals("title")) {
						title = item.getString();
						System.out.println("제목 : "+title);
//						키, 값으로 가능 name = fieldName , valaue = getString 으로 가능
//						serviceRequest.setAttribute(name, value);
					}else if (item.getFieldName() == "content" ||(item.getFieldName()).equals("content")) {
						content = item.getString();
						System.out.println("내용 : "+content);
					}else if (item.getFieldName() == "writer" ||(item.getFieldName()).equals("writer")) {
						id = item.getString();
						System.out.println("아이디 : "+id);
					}else if (item.getFieldName() == "perPageNum" ||(item.getFieldName()).equals("perPageNum")) {
						strPerPageNum = item.getString();
					}
					
					
				}else {
					System.out.println(String.format("파일형식인 파라미터 - 파라미터명: %s, 파일 : %s , 파일크기: %s bytes", item.getFieldName(), item.getName(), item.getSize()));
					if(item.getSize() > 0) {
						int index = item.getName().lastIndexOf(separator);
						fileName = item.getName().substring(index + 1);
						File uploadFile = new File(realSavePath+separator+fileName);
						item.write(uploadFile);
						if(uploadFile.toString() != null && !uploadFile.toString().equals("")) {
							boardFileUploadVO = new BoardFileUploadVO();
							boardFileUploadVO.setFileName(savePath+fileName);
							boardFileUploadVO.setFileSize(item.getSize());
							boardFileUploadVO.setRealSavePath(realSavePath+separator+fileName);
							
							listBoardFileUploadVO.add(boardFileUploadVO);
						}
					}
				}//end of else
			}
			if(title != null && content != null && id != null ) {
				boardVO = new BoardVO();
				boardVO.setTitle(title);
				boardVO.setContent(content);
				boardVO.setId(id);
			}
			Long no = ((BoardVO)Execute.service(boardWriteService3, boardVO)).getNo();
			Integer reuslt = 0;
			reuslt = (Integer) Execute.service(boardFileUploadService, new Object[] {listBoardFileUploadVO,no});
			
			//redirect: - url 이동 , 없으면 jsp 로 이동
			jsp = "redirect:list.do?perPageNum="+strPerPageNum;
			break;

		case "/view.do":
			String noStr = request.getParameter("no");
			no = Long.parseLong(noStr);

			String strInc = request.getParameter("inc");
			int inc = Integer.parseInt(strInc);

			//request는 요청하는 것이 모두 담겨있음 
			//"location='view.jsp?no=1'"
			//전달 받은 데이터 받기 /무조건 문자열로받아야함 / no 라는 키로 넘긴다(주소창에 나옴)
			//게시판에대한 페이지,검색정보
			PageObject pageObject = PageObject.getInstance(request);

			//댓글 페이지 정보 가져오기
			ReplyPageObject replyPageObject = new ReplyPageObject();
			String strReplyPage = request.getParameter("replyPage");
			//값이 있으면 replyPage를 넣는다
			if (strReplyPage != null)
				replyPageObject.setPage(Long.parseLong(strReplyPage));

			//글번호 받아오기
			System.out.println("글번호"+no);
			replyPageObject.setNo(no);
			//한 페이지당 보여주는 데이터의 갯수는 기본 값인 10을 그대로 사용한다.

			//게시판 글보기 데이터 가져오기
			vo = (BoardVO) Execute.service(boardViewService, new Object[]{no, inc});
			//게시판 댓글 리스트 데이터 가져오기 - board/view.jsp - com.webjjang.reply.service.ReplyListService -> dao.ReplyDAO
			@SuppressWarnings("unchecked") 
			List<ReplyVO> list = (List<ReplyVO>) Execute.service(replyListService, replyPageObject);

			//vo.content를 그대로 보여 주면 줄바꿈 무시, 여러개의 공백문자 무시 ->처리해준다 replace
			vo.setContent(vo.getContent().replace("\n", "<br>").replace(" ", "&nbsp;"));

			//강제 오류 발생
			// System.out.println(10/0);

			//EL 객체를 이용해서 데이터 표시하고자 한다면 JSP 저장 기본 객체 중 하나에 저장이 되어 있어야만 한다.
			//application(서버실행시) or session(상용자가 요청시생김) or request(데이터를뿌려줄때만 쓰고 버린다)  or pagecontext(해당하는jsp에서만 쓴다)
			request.setAttribute("vo", vo);
			request.setAttribute("pageObject", pageObject);
			request.setAttribute("replyPageObject", replyPageObject);
			request.setAttribute("list", list);
			
			jsp = "board/view";
			break;
			
			case "/updateForm.do":
			    pageObject = PageObject.getInstance(request);
			    
			    noStr = request.getParameter("no");
			    no = Long.parseLong(noStr);

			    // BoardViewService service = new BoardViewService();
			    //조회수 증가x
			    // BoardVO vo = service.service(no, 0);

			    //EL 객체를 이용해서 데이터 표시하고자 한다면 JSP 저장 기본 객체 중 하나에 저장이 되어 있어야만 한다.
			    //application(서버실행시) or session(상용자가 요청시생김) or request(데이터를뿌려줄때만 쓰고 버린다)  or pagecontext(해당하는jsp에서만 쓴다)
			    request.setAttribute("pageObject", pageObject);
			    request.setAttribute("vo", Execute.service(boardViewService, new Object[]{no, 0}));
				
			    //updateForm.jsp 로 이동시키기 위해 정보 저장
			    jsp = "board/updateForm";
				break;
				
			case "/update.do":
				savePath = "/upload/image/";
				
				realSavePath = request.getServletContext().getRealPath(savePath);
				System.out.println("realSavePath: " +realSavePath);
				maxSize = 1024 * 1024 * 20;
				
				multi = new MultipartRequest(request, realSavePath, maxSize, "utf-8", new DefaultFileRenamePolicy() );

				
				String strNo = multi.getParameter("no");
				no = Long.parseLong(strNo);


				title = multi.getParameter("title");
				content = multi.getParameter("content");
				id = multi.getParameter("id");
				String filesystemName = multi.getFilesystemName("image");
				fileName = null;

				if(filesystemName == null || filesystemName.equals("")) fileName = null;
				else fileName = savePath+filesystemName;
				
				String del = multi.getParameter("del");
				
				
				//페이지 검색 정보 받기(multi 용으로 메서드 만듦)
				CategoryPageObject categoryPageObjectForMulti = new CategoryPageObject();
				categoryPageObjectForMulti = CategoryPageObject.getMultiInstance(multi);

				vo = new BoardVO();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setId(id);
				vo.setFileName(fileName);

				//jsp(=이전controller역할) -> BoardUpdateService -> BoardDAO
				// BoardUpdateService service = new BoardUpdateService();
				// int result = service.service(vo);
				int result = (Integer)Execute.service(boardUpdateService, vo);

				if(result == 1) System.out.println("게시판 글 수정이 되었습니다");
				else  throw new Exception("게시판 글 수정이 되지 않았습니다 (정보를 확인해 주세요)");

				if(result == 1 && filesystemName !=null && !filesystemName.equals("")) {
					new File(request.getServletContext().getRealPath(del)).delete();
				}
				
				//DB에 공지 등록 처리 - BoardUpdateservice -BoardDAO 기존 프로젝트에 썻던걸 사용
				
				//response는 dispacher 에 있어서 넘겨서 처리한다
				//수정 처리 후 이동할 페이지 정보를"redirect:" 넘겨준다 - 스프링에서 이렇게쓴다
				jsp = "redirect:view.do?no="+no+"&inc=0&page="+categoryPageObjectForMulti.getPage()+"&perPageNum="+categoryPageObjectForMulti.getPerPageNum()+"&key="+categoryPageObjectForMulti.getKey()+"&word="+categoryPageObjectForMulti.getWord();
				
				break;
				
			case "/delete.do":
				
				strPerPageNum = request.getParameter("perPageNum");

				
				noStr = request.getParameter("no");
				no = Long.parseLong(noStr);
				
				del = request.getParameter("del");
				
				File delFile = new File(request.getServletContext().getRealPath(del));
				//DB에 공지 등록 처리 - NoticeDelteservice  기존 프로젝트에 썻던걸 사용
				result = (Integer)Execute.service(boardDeleteService, no);

				if(result == 1)delFile.delete();
				jsp= "redirect:list.do?&perPageNum="+strPerPageNum;
				
				break;
			
		default:
			throw new Exception("잘못된 페이지를 요청하셨습니다");
		}
		


		
		return jsp;
	}

}
