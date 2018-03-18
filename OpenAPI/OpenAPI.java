package OpenAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jdom.input.SAXBuilder;

public class OpenAPI {
	public static void main(String[]args){
		
		// reference : https://www.journaldev.com/7146/apache-httpclient-example-closeablehttpclient
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		// URL 설정
		String url = EnumURLOnTourAPI.SELECT_AREA_BASED_LIST.getURL();
		url += "?";
		url += EnumParameterOnTourAPI.KEY.getKey() + "=" + EnumParameterOnTourAPI.KEY.getValue();
		url += "&";
		url += EnumParameterOnTourAPI.MOBILE_APP.getKey() + "=" + EnumParameterOnTourAPI.MOBILE_APP.getValue();
		url += "&";
		url += EnumParameterOnTourAPI.MOBILE_OS.getKey() + "=" + EnumParameterOnTourAPI.MOBILE_OS.getValue();
		
		System.out.println(url);
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");	// UTF-8 인코딩
		
		CloseableHttpResponse httpResponse;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			
			// Http 통신 POST 방식 이용
			httpResponse = httpClient.execute(httpGet);
			isr = new InputStreamReader(httpResponse.getEntity().getContent());
			br = new BufferedReader(isr);
			
			// 한 줄씩 읽기 위함
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			
			/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
			 * 
			 * StringBuffer로 받은 데이터들은 기본 XML 방식
			 * JSON으로 받기 위해선parameter 값에 JSON 타입 형식 추가
			 * StringBuffer로 받은 데이터를 통해서 JSON 객체로 매핑
			 * 
			 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
			SAXBuilder saxBuilder = new SAXBuilder();
			
			System.out.println(response.toString());
			
			br.close();
			httpClient.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
