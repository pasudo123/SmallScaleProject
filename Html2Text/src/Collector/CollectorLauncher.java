package Collector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

public class CollectorLauncher {
	
	private StringBuilder textBuilder = new StringBuilder();
	private static HashSet<String> set = new HashSet<String>();
	
	public void init(){
		UsefulTag[]usefulTags = UsefulTag.values();
		for(UsefulTag usefulTag : usefulTags){
			set.add(usefulTag.getTagName());
		}
	}
	
	@Test
	public void getHtmlDocument(){
		Document document;
		
		try {
//			File file = new File("C:/Users/Daumsoft/Desktop/test.html");
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while((line = br.readLine()) != null){
//				sb.append(line);
//			}
//			
//			document = Jsoup.parse(sb.toString());
//			br.close();
			
			document = Jsoup.connect("http://aventure.tistory.com/59").get();
			Element body = document.body();
			List<Element> elementList = body.children();
			
			for(Element element : elementList){
				
				String tagName = element.tagName();
				
				/**
				 * 기본적으로 불필요 태그삭제 모두 삭제
				 * **/
				if(tagName.equalsIgnoreCase(UselessTag.USELESS_SCRIPT.getTagName()))
					continue;
				if(tagName.equalsIgnoreCase(UselessTag.USELESS_STYLE.getTagName()))
					continue;
				if(tagName.equalsIgnoreCase(UselessTag.USELESS_IFRAME.getTagName()))
					continue;
				
				Element usefulElement = element;
				String htmlLine = usefulElement.toString();
				htmlLine = htmlLine.replaceAll("\n", "").trim();
				htmlLine = htmlLine.replaceAll("\\s+", " ");
				
				if(!element.hasText())
					continue;
				
				htmlLine = htmlLine.replace("<( )*%s([^>])*?>", "<%s>");
				
				// (1) 변환 문자 (엔티티)
				htmlLine = htmlLine.replaceAll("&nbsp;", " ");
				htmlLine = htmlLine.replaceAll("&amp;", "&");
				htmlLine = htmlLine.replaceAll("&quot;", "\"");
				htmlLine = htmlLine.replaceAll("&lt;", "<");
				htmlLine = htmlLine.replaceAll("&gt;", ">");
				
				String[]elements = htmlLine.split(" ");
				System.out.println(Arrays.toString(elements));
				
				for(int i = 0; i < elements.length; i++){
					
					// (2) 개행 및 띄어쓰기
					if(elements[i].equals(""))
						textBuilder.append(" ");
					else if(elements[i].equals("<br>"))
						textBuilder.append("\n");
					else if(elements[i].equals("<p>"))
						textBuilder.append("\n");
					else if(elements[i].equals("</p>"))
						textBuilder.append("\n");
					else{
						
						// 테이블 관련 태그 확인 후 삽입
						if(elements[i].equals("<table>") || 
						   elements[i].equals("</table>") || 
						   elements[i].equals("<th>") ||
						   elements[i].equals("</th>") ||
						   elements[i].equals("<tr>") ||
						   elements[i].equals("</tr>") ||
						   elements[i].equals("<td>") ||
						   elements[i].equals("</td>") ||
						   elements[i].equals("<thead>") || 
						   elements[i].equals("</thead>") ||
						   elements[i].equals("<tbody>") ||
						   elements[i].equals("</tbody>")){
							textBuilder.append(elements[i]);
						}
						// 그외 태그 무시
						else if(!isUselessTag(elements[i])){
							textBuilder.append(elements[i] + " ");
						}
					
					}
				}
			}// for()
			
			System.out.println("== textBuilder =============================");
			System.out.println(textBuilder);
			System.out.println("============================================");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void process(StringBuilder sb, String htmlLine, int tableWidth){
		
	}
	
	private boolean isUselessTag(String element){
		Pattern pattern = Pattern.compile("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>");
		
		Matcher matcher = pattern.matcher(element);
		
		if(matcher.find())
			return true;
		return false;
	}
	
	
	// 현재 쓰지 않는 메소드
	// 유효한 요소에 글자가 있는 경우
	//	if(usefulElement.hasText()){
	//		drillTag(usefulElement);
	
	//	}// if()
//	public void drillTag(Element usefulElement){
//		Elements children = usefulElement.children();
//		int size = children.size();
//		
//		System.out.println("=================================");
//		System.out.println("== userfulElement : \n" + usefulElement);
//		System.out.println("== children : \n" + children);
//		System.out.println("== size : " + size);
//		
//		System.out.println(">>> " + usefulElement.getElementsByTag("p").toString());
//		
//		if(size == 0){
//			new Scanner(System.in).next();
//			builder.append(usefulElement.text());
//			System.out.println("== tag : " + usefulElement.tag());
//			System.out.println("== builder : " + builder);
//		}
//		if(size == 1){
//			drillTag(children.eq(0).get(0));
//		}
//		else{
//			for(Element element : children){
//				if(element.hasText())
//					drillTag(element);
//			}
//		}
//	}
}
