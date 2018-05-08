package Collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class CollectorTest {
	ArrayList<StringBuilder> builderList = new ArrayList<StringBuilder>();
	
	@Test
	public void Collect(){
		Document document = null;
		
		display();
		
		try {
			document = Jsoup.connect("http://www.daumsoft.com/contextualCA.html").get();
			Element body = document.body();
			body.select("script").remove();
			body.select("style").remove();
			body.select("label").remove();
			
			List<Element> elementList = body.children();
			
			for(Element element : elementList){
				String s = preprocess(element);
				process(s);
			}
			
			for(int i = 0; i < builderList.size(); i++){
				System.out.println(builderList.get(i));
			}
			
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
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

		int newLine = 0;
		builderList.add(new StringBuilder());
		
		for(int i = 0; i < lineElement.length; i++){
			String s = lineElement[i];
			
			/** 엔티티 및 태그 변경 **/
			
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
			
			/** 테이블  **/
			
			else if(s.equalsIgnoreCase("[table]")){
				builderList.add(new StringBuilder());
				newLine++;
				
				builderList.get(newLine).append("+----------------------------------------------------------------------------------------+");
				
				builderList.add(new StringBuilder());
				newLine++;
			}
			
			else if(s.equalsIgnoreCase("[/table]")){
				builderList.get(newLine).append("+----------------------------------------------------------------------------------------+");

				builderList.add(new StringBuilder());
				newLine++;
			}
			
			else if(s.equalsIgnoreCase("[th]") || s.equalsIgnoreCase("[tbody]") || s.equalsIgnoreCase("[thead]")){
				continue;
			}
			
			else if(s.equalsIgnoreCase("[/th]") || s.equalsIgnoreCase("[/tbody]") || s.equalsIgnoreCase("[/thead]")){
				continue;
			}
			
			else if(s.equalsIgnoreCase("[tr]")){
				builderList.get(newLine).append("|");
			}
			
			else if(s.equalsIgnoreCase("[/tr]")){
				builderList.get(newLine).append("|");
				
				builderList.add(new StringBuilder());
				newLine++;
			}
			
			else if(s.equalsIgnoreCase("[td]")){
				continue;
			}
			
			else if(s.equalsIgnoreCase("[/td]")){
				if(i < lineElement.length - 1 && lineElement[i+1].equals("[/tr]"))
					continue;
				else
					builderList.get(newLine).append("|");
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
}
