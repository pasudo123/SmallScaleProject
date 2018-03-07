package edu.doubler.dao;

import java.util.List;

import edu.doubler.util.BoardPagingNumber;

public interface PagingDao {
	
	// [ subClassName 하위의 데이터 갯수 획득 ]
	public int getDataCount(String subClassName);
	
	// [ **페이징 처리** - 현재 하위 목록에 대한 리스트 획득  ]
	public List<CpcDto> getCpcDataList(BoardPagingNumber boardPagingNumber, String subClassName);
}
