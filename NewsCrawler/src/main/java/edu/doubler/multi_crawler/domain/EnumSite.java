package edu.doubler.multi_crawler.domain;

public enum EnumSite {
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * 		 [ NAVER 와 DAUM 에서 많이 본 뉴스를 조회 당일부터 __일주일간__ 데이터를 크롤링  ]
	 * 
	 * [ 대분류  ]
	 * NAVER : 많이 본
	 *  DAUM : 많이 본
	 * 
	 * [ 소분류  ]
	 * NAVER : 정치, 경제, 사히, 생활/문화, 세계, IT/과학, 연예, 스포츠
	 *  DAUM : 뉴스, 연예, 스포츠
	 * 
	 * [ 개수  ]
	 * NAVER : 소분류에서 각각 10개
	 *  DAUM : 소분류에서 각각 10개
	 *  
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	NAVER("http://news.naver.com/main/ranking/popularDay.nhn?rankingType=popular_day"){
		
	},
	DAUM("http://media.daum.net/ranking/popular/"){
		
	};
	
	private String uri = null;
	private EnumSite(){}
	private EnumSite(String uri){
		this.uri = uri;
	}
	
	public String getSiteURI(){
		return uri;
	}
}
