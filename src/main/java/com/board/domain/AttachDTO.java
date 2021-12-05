package com.board.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttachDTO extends CommentDTO {
	
	/* 파일 번호 */
	private Long idx;
	
	/* 게시글 번호 */
	private Long boardIdx;
	
	/* 원본 파일명 */
	private String originalName;
	
	/* 저장 파일명 */
	private String saveName;
	
	/* 파일 크기 */
	private long size;
	
	
	
}
