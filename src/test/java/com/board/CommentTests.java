package com.board;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.board.domain.CommentDTO;
import com.board.paging.Criteria;
import com.board.service.CommentService;

@SpringBootTest
public class CommentTests {

	@Autowired
	private CommentService commentService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testOfRegisterComment() {
		int number = 20;

		for (int i = 1; i <= number; i++) {
			CommentDTO params = new CommentDTO();
			params.setBoardIdx((long) 1790);
			params.setContent(i + "번 댓글을 추가합니다.");
			params.setWriter(i + "번 회원");
			commentService.registerComment(params);
		}

		logger.debug("댓글 " + number + "개가 등록되었습니다.");
	}
	
	@Test
	public void testOfDeleteComment() {
		commentService.deleteComment((long) 1);
		getCommentList();
	}
	
	@Test
	public void getCommentList() {
		CommentDTO params = new CommentDTO();
		Criteria criteria = new Criteria();
		params.setBoardIdx((long) 60402);
		
		commentService.getCommentList(params, criteria);
	}
	
	

}
