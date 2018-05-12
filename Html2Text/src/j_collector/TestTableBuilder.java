package j_collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TestTableBuilder {
	private int rowSize = 0;
	private int colSize = 0;
	private int col = 1;	// 0 인덱스 생략
	private int row = 1;	// 0 인덱스 생략
	
	private static final String SPACE_ON_START = String.format("%1s", "");
	private static final String SPACE_ON_END = String.format("%3s", "");
	
	private static int lineIndex = 0;
	
	private int[]columnSize = null;
	
	public void setTableCellSize(int rowSize, int colSize){
		this.rowSize = rowSize;
		this.colSize = colSize;
		this.col = 1;
		this.row = 1;
	}
	
	/** 테이블 Element 를 이차원 배열로 변환 **/
	public void settingTableCell(Element element, TableCell[][] tableCell){
		// 일차원 컬렉션리스트, 요소 하나씩 저장하기 위함
		ArrayList<TableCell> list = new ArrayList<TableCell>();
		HashMap<String, Dummy> map = new HashMap<String, Dummy>();
		
		Elements trs = element.select("tr");
		
		for(int trIndex = 0; trIndex < trs.size(); trIndex++){
			int originRow = trIndex + 1;

			Element rowElement = trs.get(trIndex);
			Elements tds = rowElement.children();

			for(int tdIndex = 0; tdIndex < tds.size(); tdIndex++){
				int originCol = tdIndex + 1;
				
				Element cellElement = tds.get(tdIndex);
				String colSpan = cellElement.getElementsByAttribute("colspan").attr("colspan");
				String rowSpan = cellElement.getElementsByAttribute("rowspan").attr("rowspan");
				
				int attrColSpan = 1;
				int attrRowSpan = 1;
				
				// colSpan & rowSpan 이 1이 아니다.
				if(!colSpan.equals(""))
					attrColSpan = Integer.parseInt(colSpan);
				if(!rowSpan.equals(""))
					attrRowSpan = Integer.parseInt(rowSpan);
				
				String name = cellElement.text();
				name = SPACE_ON_START + cellElement.text() + SPACE_ON_END;
				TableCell cell = new TableCell(name);
				cell.setColSpan(attrColSpan);
				cell.setRowSpan(attrRowSpan);
				
				// 병합 셀  생략 위함 : 해시맵 저장
				if (attrRowSpan != 1 || attrColSpan != 1) {
					int rowRange = originRow + attrRowSpan - 1;
					int colRange = originCol + attrColSpan - 1;
					for (int r = originRow; r <= rowRange; r++) {
						for (int c = originCol; c <= colRange; c++) {
							if (r == originRow && c == originCol)
								continue;
							map.put(r + "&" + c, new Dummy());
						}
					}
				}
				
				// 1차원 컬렉션리스트에 추가
				list.add(cell);
			}// for() : td (열)
		}// for() : tr (행)
		
		// 데이터 삽입
		int index = 0;
		row = 1; col = 1;
		for(int r = row; r <= rowSize; r++){
			for(int c = col; c <= colSize; c++){
				if(!isExistMap(map, r, c)){
					tableCell[r][c] = list.get(index++);
				}
				else{
					tableCell[r][c] = new TableCell(null);
				}
			}
		}
	}
	
	private boolean isExistMap(HashMap<String, Dummy> map, int row, int col){
		if(map.get(row + "&" + col) != null)
			return true;
		
		return false;
	}
	
	// 테이블 콘솔로 출력
	public StringBuilder printTable(StringBuilder textBuilder[], TableCell[][] tableCell){
		
		textBuilder = new StringBuilder[2*tableCell.length + 2];
		
		calcColumnSize(tableCell);
		printData(textBuilder, tableCell);
		
		return null;
	}
	
	private void calcColumnSize(TableCell[][]tableCell){
		// 1 부터 시작
		int[]headerSizes = new int[tableCell[0].length];
		int rowSize = tableCell.length;
		int colSize = tableCell[0].length;
		Arrays.fill(headerSizes, 0);
		
		// 각각의 헤더 열들의 가장 긴 byte 획득
		for(int col = 1; col < colSize; col++){
			for(int row = 1; row < rowSize; row++){
				if(tableCell[row][col] != null && tableCell[row][col].getColSpan() == 1){
					
					String name = tableCell[row][col].getName();
					
					if(name == null)
						continue;
					
					int cellSize = getCellSize(name);
					
					if(headerSizes[col] < cellSize){
						headerSizes[col] = cellSize;
					}
				}
			}// for()
		}// for()
		
		columnSize = headerSizes;
	}
	
	private void printHeaderDivide(StringBuilder textBuilder, String format) {
		
		// 시작 '+' (1개)
		textBuilder.append(format.charAt(0));
		for (int column = 1; column < columnSize.length; column++) {
			
			// 글자 하이픈 '-'
			for (int s = 1; s <= columnSize[column]; s++)
				textBuilder.append(format.charAt(1));
			
			// 끝 '+' (1개)
			textBuilder.append(format.charAt(0));
		}
	}

	private void printData(StringBuilder[]textBuilder, TableCell[][] tableCell){
		int initRow = 1;
		int initCol = 1;
		int dataRow = 1;
		int lineRow = 0;
		
		// 가장 상단에 값 삽입
		textBuilder[1] = new StringBuilder();
		printHeaderDivide(textBuilder[1], "+-");
		
		for(int r = initRow; r < tableCell.length; r++){
			
			dataRow = r * 2;
			textBuilder[dataRow] = new StringBuilder();
			
			for(int c = initCol; c < tableCell[r].length; c++){
				
				String name = tableCell[r][c].getName();
				int colSpan = tableCell[r][c].getColSpan();
				int rowSpan = tableCell[r][c].getRowSpan();
				
				if(name != null){
					
					// 글자 추가
					if(c == initCol)
						textBuilder[dataRow].append(String.format("%s", "|"));
					
					textBuilder[dataRow].append(name);
					
					int size = columnSize[c] - getCellSize(name);
					for(int i = 1; i <= size; i++)
						textBuilder[dataRow].append(String.format("%s", ""));
					textBuilder[dataRow].append(String.format("%s", "|"));
					
				}// if ( 셀 한칸  )
				
				if(name == null){
					
				}// if ( 셀 병합 )
				
			}// for (행)
			
			System.out.println(textBuilder[1]);
			System.out.println(textBuilder[dataRow]);
		}
		
		System.out.println(textBuilder[1]);
		System.out.println();
		System.out.println();
	}
	
	private int getCellSize(String name){
		
		Pattern pattern = Pattern.compile("^[가-힣]*$");
		int cellSize = 0;
		
		for(int i = 0; i < name.length(); i++){
			char ch = name.charAt(i);
			Matcher matcher = pattern.matcher(String.valueOf(ch));
			
			if(matcher.find())
				cellSize += 2;
			else
				cellSize += 1;
		}
		
		return cellSize;
	}
}
