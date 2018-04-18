package com.doubler.board.util;

public class BoardSequenceNumber {
	
	public int getContentNumber(int contentNumber){
		// 게시글이 없는 경우
		if(contentNumber == 0){
			return 0;
		}
		return contentNumber;
	}
	
	public int getPlusContentNumber(int contentNubmer){
		return (contentNubmer+1);
	}
	
}
