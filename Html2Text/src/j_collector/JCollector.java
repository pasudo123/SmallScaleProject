package j_collector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JCollector {
	
//	@Test
//	public void TestAOnTable(){
//		// 예제 table
//		String path = "src/resource/table1.html";
//		TestA(path);
//	}
	
	@Test
	public void TestOnInternet(){
		String path = "http://aventure.tistory.com/59";
		TestA(path);
	}
	
	// HTML 을 String 으로 변환
	private String htmlToString(String path){
		StringBuilder sb = null;
		
		try{
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			sb = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	/** 내가 만든 html 문서 테스트 **/
	private void TestA(String path){
		Document document = null;
		
		if(path.contains("http"))
			try {
				document = Jsoup.connect(path).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
		else{
			String htmlString = htmlToString(path);
			document = Jsoup.parse(htmlString);
		}
		
		Element body = document.body();

		// [table 태그 추출] 후 [table 태그 제거]
		Elements tables = body.select("table");
		
		// 불필요 태그 전부 삭제
		body.select("script").remove();
		body.select("style").remove();
		body.select("table").remove();
		body.select("label").remove();
		body.select("iframe").remove();
		
		// html 태그 엔티티 변환 및 정규식 삭제
		String content = preprocess(body);
		ArrayList<StringBuilder> builderList = process(content);
		
//		System.out.println("텍스트 출력 시도 (텍스트 없으면 안나옴)==");
		for(int i = 0; i < builderList.size(); i++)
			System.out.println(builderList.get(i));
//		System.out.println("텍스트 출력 완료 ==");
		
		ArrayList<TableCell[][]> tableList = new ArrayList<TableCell[][]>();
		TableCell[][] tableCell = null;
		TableBuilder tableBuilder = new TableBuilder();
		TestTableBuilder testTableBuilder = new TestTableBuilder();
		
		// table 접근 및 TableCell 이차원 배열 형성
		for(Element table : tables){
			int rowSize = (table.select("tr").size());
			int colSize = table.select("tr").first().children().size();
			
			tableCell = new TableCell[rowSize+1][colSize+1];
			tableBuilder.setTableCellSize(rowSize, colSize);
			tableBuilder.settingTableCell(table, tableCell);
			
//			테스트 : testTableBuilder
//			testTableBuilder.setTableCellSize(rowSize, colSize);
//			testTableBuilder.settingTableCell(table, tableCell);
			tableList.add(tableCell);
		}
		
		// 테이블을 텍스트 변환
		StringBuilder builder = null;
		StringBuilder partBuilder[] = null;
		
		for(int i = 0; i < tableList.size(); i++){
			TableCell[][] t = tableList.get(i);
			builder = tableBuilder.printTable(t);
			System.out.println(builder);

//			new TestTableBuilder().printTable(partBuilder, t);
		}	
		
//		display(tableList);
	}
	
	private String preprocess(Element body){
		String elementString = body.toString();
		
		elementString = elementString.replaceAll("<p>", " [pn] ");
		elementString = elementString.replaceAll("</p>", " [/pn] ");
		elementString = elementString.replaceAll("<br>", " [brn] ");
		elementString = elementString.replaceAll("&nbsp;", " [nbsp] ");
		elementString = elementString.replaceAll("&amp;", " [&] ");
		elementString = elementString.replaceAll("&quot;", " [\"] ");
		elementString = elementString.replaceAll("&lt;", " [<] ");
		elementString = elementString.replaceAll("&gt;", " [>] ");
		
		// HTML 태그 제거
		elementString = elementString.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		
		// 헤더 제거
		elementString = elementString.replaceAll("<h[1-5][^>]*>|<\\/h[1-5]>", "");
		
		// 주석 제거 (<!---->)
		elementString = elementString.replaceAll("<!--(.*?)-->","");
				
		// 개행 제거
		elementString = elementString.replaceAll("\n", "").trim();
		
		// 공백 제거
		elementString = elementString.replaceAll("\\s+", " ");
		
		return elementString;
	}
	
	private ArrayList<StringBuilder> process(String content){
		String input = content.trim();
		
		// 한 줄씩 StringBulider 로 append 하기 위함
		ArrayList<StringBuilder> builderList = new ArrayList<StringBuilder>();
		builderList.add(new StringBuilder());
		int newLine = 0;
		
		String[]element = input.split(" ");
		for(int i = 0; i < element.length; i++){
			String s = element[i];
			
			if(s.equals(""))
				continue;
			
			/** ㅡㅡㅡㅡㅡㅡㅡ 엔티티 및 태그 변경 ㅡㅡㅡㅡㅡㅡㅡ **/
			if(s.equalsIgnoreCase("[pn]") || s.equalsIgnoreCase("[/pn]") || s.equalsIgnoreCase("[brn]")){
				builderList.add(new StringBuilder());
				newLine++;
			}
			else if(s.equalsIgnoreCase("[nbsp]"))
				builderList.get(newLine).append(" ");
			
			else if(s.equalsIgnoreCase("[&]"))
				builderList.get(newLine).append("&");
			
			else if(s.equalsIgnoreCase("[\"]"))
				builderList.get(newLine).append("\"");
			
			else if(s.equalsIgnoreCase("[<]"))
				builderList.get(newLine).append("<");
			
			else if(s.equalsIgnoreCase("[>]"))
				builderList.get(newLine).append(">");

			// 문자
			else{
				s = s.trim();
				builderList.get(newLine).append(s + " ");
			}
		}
		
		return builderList;
	}
	
	private void display(ArrayList<TableCell[][]> tableList){
		// 출력문
		for(int i = 0; i < tableList.size(); i++){
			TableCell[][] t = tableList.get(i);
			
			for(int j = 1; j < t.length; j++){
				for(int k = 1; k < t[j].length; k++){
					System.out.printf("%s%10s", t[j][k], "");
				}
				System.out.println();
			}
			
			System.out.println("=====");
		}
	}
}
