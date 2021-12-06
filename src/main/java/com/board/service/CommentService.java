package com.board.service;

import java.util.List;

import com.board.domain.CommentDTO;
import com.board.paging.Criteria;

public interface CommentService {

	public boolean registerComment(CommentDTO params);
	
	public boolean deleteComment(Long idx);
	
	public List<CommentDTO> getCommentList(CommentDTO params, Criteria criteria);
	
}
