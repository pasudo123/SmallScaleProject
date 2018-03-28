package news_crawler.crawler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import news_crawler.domain.News;

public interface Crawler {
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * @author DoubleR
	 * 
	 * 1) Selenium WebDriver 는 많은 언어를 지원한다. 해당 언어마다 자체 클라이언트 드라이버가 존재
	 * 자바언어를 지원하는 클라이언트 드라이버 설치 및 압축 해제
	 * 
	 * 2) 자바 jdk 파일 경로에 jre >> ext 를 거쳐서 .jar 파일 복사
	 * 
	 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 크롬 드라이버 실행 파일을 환경변수 Path 에 추가할 수 있다.
	 * 따로 코드내에서 시스템 설정 없이 작동 가능
	 * 
	 * 크롬 드라이버 실행 파일을 시스템 환경에 맞게 명시적 설정
	 * (1) 경로 설정
	 * (2) ChromeDriver 클래스를 인스턴스화
	 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	static final String EXE_PATH = "src\\news_crawler\\web-driver\\chromedriver.exe";
	static final String SYSTEM_PROPERTY = "webdriver.chrome.driver";
	
	default WebDriver setWebDriver(String URI){
		System.setProperty(SYSTEM_PROPERTY, EXE_PATH);
		WebDriver webDriver = new ChromeDriver();
		webDriver.get(URI);
		
		return webDriver;
	}
	
	/*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 			Find 에 대한 메소드 전략은 'By' 라는 로케이터 또는 쿼리 객체를 이용
	 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	public News parsingData(String uri);
}
