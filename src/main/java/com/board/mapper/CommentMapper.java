package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.board.domain.CommentDTO;
import com.board.paging.Criteria;

@Mapper
public interface CommentMapper {
	
	public int insertComment(CommentDTO params);
	
	public CommentDTO selectCommentDetail(Long idx);
	
	public int updateComment(CommentDTO params);
	
	public int deleteComment(Long idx);
	
	public List<CommentDTO> selectCommentList(@Param("params") CommentDTO params, @Param("criteria") Criteria criteria);
	
	public int selectCommentToatlCount(CommentDTO params);
	
}
