package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
public class ServiceTests {

	@Autowired
	private BoardService boardService;

	@Test
	public void testOfRegisterBoard() {
//		BoardDTO params = new BoardDTO();
//		params.setTitle("서비스 테스트 제목");
//		params.setContent("서비스 테스트 내용");
//		params.setWriter("서비스 테스트 작성자");
//
//		boolean result = boardService.registerBoard(params);
//
//		System.out.println("결과는 : " + result + "입니다.");

		BoardDTO board = boardService.getBoardDetail((long) 2);
		board.setTitle("2번 게시글 서비스 수정 제목");
		board.setContent("2번 게시글 서비스 수정 내용");
		board.setWriter("2번 게시글 서비스 수정 작성자");

		boolean result = boardService.registerBoard(board);

		System.out.println("결과는 : " + result + "입니다.");
	}

	@Test
	public void testOfGetBoardDetail() {
		BoardDTO board = boardService.getBoardDetail((long) 2);
		try {
			String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

			System.out.println("=========================");
			System.out.println(boardJson);
			System.out.println("=========================");

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testOfDeleteBoard() {
		boolean result = boardService.deleteBoard((long) 2);

		if(result == true) {
			BoardDTO board = boardService.getBoardDetail((long) 2);
			try {
				String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

				System.out.println("=========================");
				System.out.println(boardJson);
				System.out.println("=========================");

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testOfGetBoardList() {
		List<BoardDTO> boardList = boardService.getBoardList();
		
		if(boardList.isEmpty() == false) {
			for (BoardDTO board : boardList) {
				System.out.println("====================");
				System.out.println(board.getTitle());
				System.out.println(board.getContent());
				System.out.println(board.getWriter());
				System.out.println("====================");
			}
		} else {
			System.out.println("반환된 리스트가 없습니다.");
		}
	}
	
}
