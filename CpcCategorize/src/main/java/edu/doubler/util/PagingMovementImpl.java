package edu.doubler.util;

public class PagingMovementImpl implements PagingMovement {
	
	// 현재 서버의 페이징 처리 객체
	private BoardPaging boardPaging = null;
	
	public PagingMovementImpl(BoardPaging boardPaging){
		this.boardPaging = boardPaging;
	}
	
	@Override
	public BoardPaging chooseMovement(String paging) {
		if(paging.equals("begin"))
			return moveBeginBlock();
		
		if(paging.equals("prev"))
			return movePrevBlock();
		
		if(paging.equals("next"))
			return moveNextBlock();
		
		if(paging.equals("end"))
			return moveEndBlock();
		
		int pageNum = Integer.parseInt(paging);
		
		return movePage(pageNum);
	}
	
	@Override
	public BoardPaging moveBeginBlock() {
		// 처음 페이지 블록
		boardPaging.setCurrentBlock(boardPaging.getFirstBlock());
		boardPaging.setCurrentPage(boardPaging.getFirstPageNum());
		
		return boardPaging;
	}

	@Override
	public BoardPaging movePrevBlock() {
		// 이전 페이지 블록으로
		System.out.println("before : " + boardPaging.getCurrentBlock());
		boardPaging.setCurrentBlock(boardPaging.getCurrentBlock() - 1);
		System.out.println("after : " + boardPaging.getCurrentBlock());
		boardPaging.setCurrentPage(boardPaging.getCurrentBlock() * BoardPaging.PRINT_PAGE_COUNT);
		
		return boardPaging;
	}

	@Override
	public BoardPaging moveNextBlock() {
		// 다음 페이지 블록으로
		boardPaging.setCurrentBlock(boardPaging.getCurrentBlock() + 1);
		boardPaging.setCurrentPage(boardPaging.getCurrentBlock()*BoardPaging.PRINT_PAGE_COUNT - (BoardPaging.PRINT_PAGE_COUNT - 1));
	
		return boardPaging;
	}

	@Override
	public BoardPaging moveEndBlock() {
		// 마지막 페이지 블록으로
		boardPaging.setCurrentBlock(boardPaging.getLastBlock());
		boardPaging.setCurrentPage(boardPaging.getLastPageNum());;
		
		return boardPaging;
	}

	@Override
	public BoardPaging movePage(int pageNum) {
		// 해당 페이지가 현재 페이지 블록보다 큰 경우
		// 해당 페이지가 현재 페이지 블록보다 작은 경우
		// 위의 경우 특정 페이지 블록으로 이동해야 한다.
		int pageBlock = 0;
		
		if(pageNum % BoardPaging.PRINT_PAGE_COUNT != 0)
			pageBlock += 1;
		
		pageBlock += pageNum / BoardPaging.PRINT_PAGE_COUNT;
		
		boardPaging.setCurrentBlock(pageBlock);
		boardPaging.setCurrentPage(pageNum);
		
		return boardPaging;
	}
}
