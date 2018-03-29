package news_crawler.crawler;

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
	
	/*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 			Find 에 대한 메소드 전략은 'By' 라는 로케이터 또는 쿼리 객체를 이용
	 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	public News parsingData(String uri);
	
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * [ ImplicityWait Command ] [ 은연중으로  ]
	 * 
	 * 셀레니움에게 페이지에서 요소를 찾을 수 없다는 예외에
	 * 대해서 예외를 던지기 이전에 일정시간을 기다리도록 할 수 있다.
	 * 
	 * WebDriver 가 즉시 가져올 수 없는 요소들을 찾는 경우 특정시간 DOM을 polling 하도록 기다린다.
	 * implicit waits 를 설정함으로써, 브라우저 내 페이지의 요소를 검색할 대기시간 설정이 가능
	 * 
	 * reference : http://toolsqa.com/selenium-webdriver/wait-commands/
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * [ FluentWait Command ] [ 부드럽고 능동적인  ]
	 * 
	 * 각각의 FluentWait 인스턴스는 조건을 기다리는 최대시간을 정의한다.
	 * 또한 NoSuchElementExceptions 와 같이 대기 중에 특정 유형의 예외를 무시하도록 설정이 가능
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * [ ExplicitWait Command ] [ 노골적으로  ]
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	// XPATH 테크닉
	// http://toolsqa.com/selenium-webdriver/choosing-effective-xpath/
	
	// Element Not Visible Exception
	// Exception : http://learn-automation.com/exceptions-in-selenium-webdriver/
	// http://learn-automation.com/solve-elementnotvisibleexception-in-selenium-webdriver/
}
