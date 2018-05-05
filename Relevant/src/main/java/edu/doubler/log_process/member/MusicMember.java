package edu.doubler.log_process.member;

import java.util.Random;

public class MusicMember {
	private String memberKey = null;
	private String memberGender = null;
	private String memberBirthday = null;
	
	public MusicMember(String memberKey){
		setGender();
		setBirth();
		this.memberKey = memberKey;
	}
	
	public MusicMember(String memberKey, String memberGender, String memberBirthday){
		this.memberKey = memberKey;
		this.memberGender = memberGender;
		this.memberBirthday = memberBirthday;
	}
	
	// 남/녀 선택
	private void setGender(){
		Random random = new Random();
		int gender = random.nextInt(2);
		this.memberGender = (gender >= 1)? "M":"F";
	}
	
	// 생년 선택
	private void setBirth(){
		int number = new Random().nextInt(5) + 1;
		switch(number){
			case 1:this.memberBirthday = "2000"; break;	// 2000년 대 사람
			case 2:this.memberBirthday = "1990"; break;	// 1990년 대 사람
			case 3:this.memberBirthday = "1980"; break;	// 1980년 대 사람
			case 4:this.memberBirthday = "1970"; break;	// 1970년 대 사람
			case 5:this.memberBirthday = "1960"; break;	// 1960년 대 사람
			default:this.memberBirthday = "1950";		// 1950년 대 사람
		}
	}
	
	public String getMemberKey(){
		return memberKey;
	}
	
	public String getMemberGender(){
		return memberGender;
	}
	
	public String getMemberBirthday(){
		return memberBirthday;
	}
	
	@Override
	public String toString() {
		return "MusicMember "
				+ "[memberKey=" + memberKey 
				+ ", memberGender=" + memberGender 
				+ ", memberBirthday=" + memberBirthday + "]";
	}
	
	public String toTsvString(){
		return memberKey + "\t" + memberGender + "\t" + memberBirthday;
	}
}
