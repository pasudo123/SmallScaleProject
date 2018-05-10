package Collector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CollectorTest {
	
	String plusEdge = "+";
	String hyphenEdge = "-";
	String bar = "|";
	
	ArrayList<StringBuilder> builderList = new ArrayList<StringBuilder>();
	ArrayList<String>tableBuilder = new ArrayList<String>();
	int newLine = 0;
	
	@SuppressWarnings("unused")
	private static int width;
	
	@Test
	public void testA(){
//		collect();
		tableTest("src/resource/table1.html");
		testB(tableBuilder);
		
//		String a = "aasdasd";
//		String b = "가나다라마바";
//		String c = "!(*^%$&%$^";
//		String d = ",.,,,';\"\"";
//		String space = "家羅";
//		String space2 = "ASASㄲㅃㅉ";
//		
//		System.out.println(String.format("|%s%s", "" ,a));
//		System.out.println(String.format("|%1s%s", "", b));
//		System.out.println(String.format("|%2s%s", "", c));
//		System.out.println(String.format("|%3s%s", "", d));
//		System.out.println(String.format("|%4s%s", "", space));
//		System.out.println(String.format("|%5s%s", "", space2));
//		System.out.println(String.format("|%5s%s", "", space2));
	}
	
	private void testB(ArrayList<String> tb){
		
		// tr 기준 자르기. (행)
		int rowIndex = 0;
		ArrayList<ArrayList<String>> trList = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < tb.size(); i++){
			if(tb.get(i).equalsIgnoreCase("[tr]")){
				trList.add(new ArrayList<String>());
				StringBuilder cell = new StringBuilder();
				
				for(int j = i+1; j < tb.size(); j++){
					
					if(tb.get(j).equalsIgnoreCase("[/tr]")){
						rowIndex++;
						break;
					}
					if(tb.get(j).equalsIgnoreCase("[th]") || 
					   tb.get(j).equalsIgnoreCase("[td]")){
						continue;
					}
					else if(tb.get(j).equalsIgnoreCase("[/td]") || tb.get(j).equalsIgnoreCase("[/th]")){
						trList.get(rowIndex).add(cell.toString().trim());
						cell = new StringBuilder();
					}
					else {
						cell.append(tb.get(j) + " ");
					}
				}
			}// tr : row 한 행
			
			// table 이 여러개인 경우 분류작업
			else if(tb.get(i).equalsIgnoreCase("[end]")){
				trList.add(new ArrayList<String>());
				rowIndex++;
			}
		}// for
		
		for(int i = 0; i < trList.size(); i++){
			if(trList.get(i).size() == 0){
				System.out.println("===========================================");
			}
			else{
				for(int j = 0; j < trList.get(i).size(); j++){
					System.out.print(trList.get(i).get(j) + " | ");
				}
				System.out.println("---");
			}
		}
	}
	
	@SuppressWarnings("resource")
	private void display(){
		System.out.println("table 의 너비를 설정하시오");
		System.out.print("(1)40 | (2)60 | (3)80 | (4)100 : ");
		width = Integer.parseInt(new Scanner(System.in).next());
		System.out.println();
	}
	
	private void collect(){
		Document document = null;
		
		try {
			document = Jsoup.connect("http://www.daumsoft.com/contextualCA.html").get();
			Element body = document.body();
			
			if(body.select("table").size() != 0)
				display();
			
			body.select("script").remove();
			body.select("style").remove();
			body.select("label").remove();
			
			List<Element> elementList = body.children();
			
			for(Element element : elementList){
				String s = preprocess(element);
				process(s);
			}
			
			for(int i = 0; i < builderList.size(); i++){
				if(builderList.get(i).length() != 0)
					System.out.println(builderList.get(i));
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void process(String string){
		String input = string;
		input = input.replaceAll("<!--(.*?)-->","");
		input = input.trim();
		String lineElement[] = input.split(" ");
		
		builderList.add(new StringBuilder());
		
		boolean checkTable = false;
		
		for(int i = 0; i < lineElement.length; i++){
			String s = lineElement[i];
			
			if(checkTable){
				tableBuilder.add(s);
				
				if(s.equalsIgnoreCase("[/table]")){
					tableBuilder.add("[end]");
					checkTable = false;
				}
			}
			
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
			
			/** ㅡㅡㅡㅡㅡㅡㅡ 테이블  ㅡㅡㅡㅡㅡㅡㅡ **/
			else if(s.equalsIgnoreCase("[table]")){
				checkTable = true;
				tableBuilder.add(s);

				builderList.add(new StringBuilder());
				newLine++;
				
				builderList.get(newLine).append("+-+");
				
				builderList.add(new StringBuilder());
				newLine++;
			}
			
			else if(s.equalsIgnoreCase("[/table]")){
				builderList.get(newLine).append("+-+");
				
				builderList.add(new StringBuilder());
				newLine++;
			}
			
			else if(s.equalsIgnoreCase("[tbody]") || s.equalsIgnoreCase("[thead]")){
				continue;
			}
			
			else if(s.equalsIgnoreCase("[/tbody]") || s.equalsIgnoreCase("[/thead]")){
				continue;
			}
			
			else if(s.equalsIgnoreCase("[tr]")){
				String startRow = String.format("|%1s", "");
				builderList.get(newLine).append(startRow);
			}
			
			else if(s.equalsIgnoreCase("[/tr]")){
				String endRow = String.format("%3s|", "");
				
				builderList.get(newLine).append(endRow);
				builderList.add(new StringBuilder());
				newLine++;
				
				if(i < lineElement.length - 1 && lineElement[i+1].equals("[tr]")){
					builderList.get(newLine).append("+-+");
					builderList.add(new StringBuilder());
					newLine++;
				}
			}
			
			else if(s.equalsIgnoreCase("[th]")){
				continue;
			}
			
			else if(s.equalsIgnoreCase("[/th]")){
				if(i < lineElement.length - 1 && lineElement[i+1].equals("[/tr]"))
					continue;
				else{
					String betweenRow = String.format("%3s|%1s", "", "");
					builderList.get(newLine).append(betweenRow);
				}
			}
			
			else if(s.equalsIgnoreCase("[td]")){
				continue;
			}
			
			else if(s.equalsIgnoreCase("[/td]")){
				if(i < lineElement.length - 1 && lineElement[i+1].equals("[/tr]"))
					continue;
				else{
					String betweenRow = String.format("%3s|%1s", "", "");
					builderList.get(newLine).append(betweenRow);
				}
			}
			
			else if(s.equals("")){
				continue;
			}
			
			else{
				// 문자
				builderList.get(newLine).append(s + " ");
			}
		}
	}
	
	// 태그 정리 및 제거
	private String preprocess(Element element){
		String elementString = element.toString();
		
		elementString = elementString.replaceAll("<p>", " [pn] ");
		elementString = elementString.replaceAll("</p>", " [/pn] ");
		elementString = elementString.replaceAll("<br>", " [brn] ");
		elementString = elementString.replaceAll("&nbsp;", " [nbsp] ");
		elementString = elementString.replaceAll("&amp;", " [&] ");
		elementString = elementString.replaceAll("&quot;", " [\"] ");
		elementString = elementString.replaceAll("&lt;", " [<] ");
		elementString = elementString.replaceAll("&gt;", " [>] ");
		
		elementString = elementString.replaceAll("<table[^>]*>", " [table] ");
		elementString = elementString.replaceAll("</table>", " [/table] ");
		elementString = elementString.replaceAll("<th[^>]*>", " [th] ");
		elementString = elementString.replaceAll("</th>", " [/th] ");
		elementString = elementString.replaceAll("<tr[^>]*>", " [tr] ");
		elementString = elementString.replaceAll("</tr>", " [/tr] ");
		elementString = elementString.replaceAll("<td[^>]*>", " [td] ");
		elementString = elementString.replaceAll("</td>", " [/td] ");
		elementString = elementString.replaceAll("<thead[^>]*>", " [thead] ");
		elementString = elementString.replaceAll("</thead>", " [/thead] ");
		elementString = elementString.replaceAll("<tbody[^>]*>", " [tbody] ");
		elementString = elementString.replaceAll("</tbody>", " [/tbody] ");
		
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
	
	private void tableTest(String path){
		Document document = null;
		
		try{
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			
			document = Jsoup.parse(sb.toString());
			br.close();
			
			Element body = document.body();
			
			List<Element> elementList = body.children();
			
			for(Element element : elementList){
				String s = preprocess(element);
				process(s);
			}
			
			for(int i = 0; i < builderList.size(); i++){
				if(builderList.get(i).length() != 0)
					System.out.println(builderList.get(i));
				else
					System.out.println();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
