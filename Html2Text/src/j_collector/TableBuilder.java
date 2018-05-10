package j_collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TableBuilder {
	private int rowSize = 0;
	private int colSize = 0;
	private int col = 1;	// 0 인덱스 생략
	private int row = 1;	// 0 인덱스 생략
	
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
				
				TableCell cell = new TableCell(cellElement.text());
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
		
//		System.out.println("collection Size : " + list.size());
//		System.out.println(map.toString());
		
		// 데이터 삽입
		int index = 0;
		row = 1; col = 1;
		for(int r = row; r <= rowSize; r++){
			for(int c = col; c <= colSize; c++){
				if(!isExistMap(map, r, c)){
					tableCell[r][c] = list.get(index++);
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
	public StringBuilder printTable(TableCell[][] tableCell){
		StringBuilder textBuilder = new StringBuilder();
		
		calcColumnSize(tableCell);
		printHeaderDivide(textBuilder, "+-");
		printHeaderData(textBuilder, tableCell);
		
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
				if(tableCell[row][col] != null && tableCell[row][col].colSpan() == 1){
					
					String name = tableCell[row][col].getName();
					int cellSize = getCellSize(name);
					
					if(headerSizes[col] < cellSize){
						headerSizes[col] = cellSize;
					}
				}
			}// for()
		}// for()
		
		columnSize = headerSizes;
	}
	
	private void printHeaderDivide(StringBuilder textBuilder, String format){
		textBuilder.append(format.charAt(0));
		
		for(int column = 1; column < columnSize.length; column++){
			
			// 시작 공백 '-' (1개)
			textBuilder.append(format.charAt(1));
			
			for(int s = 1; s < columnSize[column]; s++)
				textBuilder.append(format.charAt(1));
			
			// 끝 공백을 위한 '-' (3개)
			for(int i = 0; i <= 3; i++)
				textBuilder.append(format.charAt(1));
			
			textBuilder.append(format.charAt(0));
		}
		textBuilder.append("\n");
		
	}
	
	private void printHeaderData(StringBuilder textBuilder, TableCell[][] tableCell){
		row = 1;
		col = 1;
		
		System.out.println(Arrays.toString(columnSize));
		
		for(int r = row; r < tableCell.length; r++){
			for(int c = col; c < tableCell[r].length; c++){
				if(tableCell[r][c] != null && tableCell[r][c].colSpan() == 1 && tableCell[r][c].rowSpan() == 1){
					
					if(c == col){
						textBuilder.append(String.format("%s", "|"));
						textBuilder.append(String.format("%1s", " "));
					}
					
					String name = tableCell[r][c].getName();
					textBuilder.append(String.format("%s", name));
					
					// 규격을 어떻게 맞출것인가
					int cellSize = getCellSize(name);
					int size = columnSize[c] - cellSize + 1 + 3;
					textBuilder.append(String.format("%"+size+"s", ""));
					
				}
			}
			textBuilder.append("\n");
			printHeaderDivide(textBuilder, "+-");
		}
		
		System.out.println(textBuilder);
		System.out.println();
//		System.exit(1);
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
	
//	@Test
	public void testA(){
		String hangul1 = "월";
		String hangul2 = "월화";
		
		System.out.println("월 바이트 : " + hangul1.getBytes().length);
		System.out.println("월화 바이트 : " + hangul2.getBytes().length);
		
		String alph1 = "a";
		String alph2 = "ab";
		
		System.out.println("a 바이트 : " + alph1.getBytes().length);
		System.out.println("ab 바이트 : " + alph2.getBytes().length);
		
		String hyphen = "-";
		String plus = "+";
		String bar = "|";
		String space = " ";
		
		System.out.println("- 바이트 : " + hyphen.getBytes().length);
		System.out.println("+ 바이트 : " + plus.getBytes().length);
		System.out.println("| 바이트 : " + bar.getBytes().length);
		System.out.println("공백 바이트 : " + space.getBytes().length);
	
//		String test1 = "Month"; 	// 5
//		String test2 = "February";	// 8
//		String test3 = "Savings";	// 7
		
//		String test1 = "고"; 	// 3
//		String test2 = "고구";	// 6
//		String test3 = "고구마";	// 9
		
//		String test1 = "!";	// 1
//		String test2 = "@";	// 1
//		String test3 = "#";	// 1
//		String test1 = "$";	// 1
//		String test2 = "%";	// 1
//		String test3 = "^";	// 1
//		String test1 = "&";	// 1
//		String test2 = "*";	// 1
//		String test3 = "(";	// 1
//		String test1 = ")";	// 1
//		String test2 = "-";	// 1
//		String test3 = "+";	// 1
		String test1 = "_";	// 1
		String test2 = "=";	// 1
		String test3 = "[";	// 1
		
		System.out.println("test1의 바이트 : " + test1.getBytes().length);
		System.out.println("test2의 바이트 : " + test2.getBytes().length);
		System.out.println("test3의 바이트 : " + test3.getBytes().length);
	}
	
//	@Test
	public void TestB(){
//		String str = "ab가ab";
//		System.out.println(str.getBytes().length);
//		
//		String s1 = "+----------+";
//		String s2 = "| Month    |";
//		String s3 = "+----------+";
//		String s4 = "| 1월달          |";
//		
//		System.out.println(s1 + ", " + s1.getBytes().length);
//		System.out.println(s2 + ", " + s2.getBytes().length);
//		System.out.println(s3 + ", " + s3.getBytes().length);
//		System.out.println(s4 + ", " + s4.getBytes().length);
//		
//		System.out.println();
//		
		String s5 = "+----------+";
		String s6 = "| Month               |";
		String s7 = "+----------+";
		String s8 = "| 1월달                  |";
		System.out.println(s5 + ", " + s5.getBytes().length);
		System.out.println(s6 + ", " + s6.getBytes().length);
		System.out.println(s7 + ", " + s7.getBytes().length);
		System.out.println(s8 + ", " + s8.getBytes().length);
//		for(int i = 0; i < s8.length(); i++){
//			System.out.println((s8.charAt(i)+"").getBytes().length);
//		}
		
		
	}
}

class Dummy{}
