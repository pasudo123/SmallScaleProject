package Collector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

public class CollectorLauncher {
	
	private StringBuilder builder = new StringBuilder();
	
	@Test
	public void getHtmlDocument(){
		Document document;
		
		try {
			File file = new File("C:/Users/Daumsoft/Desktop/test.html");
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			
			document = Jsoup.parse(sb.toString());
			br.close();
			
//			document = Jsoup.connect("http://aventure.tistory.com/59").get();
			Element body = document.body();
			List<Element> elementList = body.children();
			
			StringBuilder parentSb = new StringBuilder();
			
			for(Element element : elementList){
				
				String tagName = element.tagName();
				
				/**
				 * 불필요 태그삭제
				 * **/
				if(tagName.equalsIgnoreCase(UselessTag.USELESS_SCRIPT.getTagName()))
					continue;
				if(tagName.equalsIgnoreCase(UselessTag.USELESS_STYLE.getTagName()))
					continue;
				if(tagName.equalsIgnoreCase(UselessTag.USELESS_IFRAME.getTagName()))
					continue;
				
				Element usefulElement = element;
				
				// 유효한 요소에 글자가 있는 경우
				if(usefulElement.hasText()){
					drillTag(usefulElement);
				}// if()
			}// for()
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drillTag(Element usefulElement){
		Elements children = usefulElement.children();
		int size = children.size();
		
		System.out.println("=================================");
		System.out.println("== userfulElement : " + usefulElement);
		System.out.println("== children : " + children);
		System.out.println("== size : " + size);
		
		System.out.println(">>> " + usefulElement.getElementsByTag("p").toString());
		
		if(size == 0){
			new Scanner(System.in).next();
			builder.append(usefulElement.text());
			System.out.println("== builder : " + builder);
		}
		if(size == 1){
			drillTag(children.eq(0).get(0));
		}
		else{
			for(Element element : children){
				if(element.hasText())
					drillTag(element);
			}
		}
	}
}
