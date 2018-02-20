package com.pasudo.submain;

public class PerformTimer {
	// 처리시간 
	private long startTime = 0;							// 시작 나노초
	private long endTime = 0;							// 종료 나노초
	private long performTime = 0;						// 수행 나노초
	private double performSecondTime = 0;				// 수행 초(소수점 3자리까지)
	private String explainLine = "파일 및 디비 입출력";		// 설명 글
	
	// 시작시간 설정
	public void start(){
		startPrint(this.explainLine);
		startTime = System.nanoTime();
	}
	
	// 종료시간 설정
	public void end(){
		endPrint(this.explainLine);
		endTime = System.nanoTime();
		
		performCalculate();
	}
	
	// 수행시간 계산
	private void performCalculate(){
		performTime = (long)(endTime - startTime);
		performSecondTime = Double.parseDouble(String.format("%.3f", performTime / Math.pow(10, 9)));
		
		performPrint();
	}
	
	// 시작 설정시 출력
	private void startPrint(String explain){
		System.out.println("------ START : " + explain + " ------");
	}
	
	// 종료 설정시 출력
	private void endPrint(String explain){
		System.out.println("------ END : " + explain + " ------");
	}
	
	private void performPrint(){
		System.out.println("Perform Time (nano) : " + performTime + " nanosec");
		System.out.println("Perform Time (second) : " + performSecondTime + " sec");
		System.out.println("======================================================\n");
	}
}
