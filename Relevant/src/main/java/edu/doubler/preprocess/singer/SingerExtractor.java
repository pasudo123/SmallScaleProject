package edu.doubler.preprocess.singer;

import java.util.Random;

public class SingerExtractor {
	public static void main(String[]args){
		Random random = new Random();
		
		for(int i = 0; i <= 30; i++){
			int res = random.nextInt(5) + 1;
			System.out.println(res);
		}
	}
}
