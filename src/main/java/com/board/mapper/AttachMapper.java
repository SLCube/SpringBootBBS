package com.board.mapper;

import java.util.List;

import com.board.domain.AttachDTO;

public interface AttachMapper {

	public int insertAttach(List<AttachDTO> attachList);
	
	public AttachDTO selectAttachDetail(Long idx);
	
	public int deleteAttach(Long idx);
	
	public List<AttachDTO> selectAttachList(Long boardIdx);
	
	public int selectAttachTotal(Long boardIdx);
	
}
