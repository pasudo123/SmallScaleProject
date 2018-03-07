package edu.doubler.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.doubler.dao.CpcDto;
import edu.doubler.dao.PagingDao;
import edu.doubler.util.BoardPagingNumber;

@Service
public class PagingServiceImpl implements PagingService{
	
	@Autowired
	private PagingDao pagingDao;
	private static BoardPagingNumber boardPagingNumber = new BoardPagingNumber();
	
	@Override
	public int getDataCount(String subClassName) {
		return pagingDao.getDataCount(subClassName);
	}

	@Override
	public List<CpcDto> getCpcDataList(Map<String, Integer> map, int dataCount, String subClassName) {
		// 계산식.
		int currentPage = map.get("currentPage");
		int printContentCount = map.get("PRINT_CONTENT_COUNT");
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * - LIKE 연산자를 이용
		 * - ROWNUM 을 통한 추출
		 * - ROWNUM 오름차순으로 끊어서 추출
		 * 
		 * ex)  1 ~ 10
		 * 	   11 ~ 20
		 *     21 ~ 30
		 *     
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		
		// 서비스 로직에서  startNum & endNum 설정
		// 낮은 번호부터 큰 번호 (오름차순)
		int startNum = (currentPage * printContentCount) - (printContentCount - 1);
		int endNum = currentPage * printContentCount;
		
		boardPagingNumber.setStartNum(startNum);
		boardPagingNumber.setEndNum(endNum);
		
		return pagingDao.getCpcDataList(boardPagingNumber, subClassName);
	}
}
