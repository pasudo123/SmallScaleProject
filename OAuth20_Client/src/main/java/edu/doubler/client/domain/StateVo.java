package edu.doubler.client.domain;

/**
 * [ Client ㅡㅡ> Resource Owner ]
 * [ Resource Owner ㅡㅡ> Client ]
 * 
 * 이 과정에서 Client 에서 state 를 저장하고, 
 * Resource Owner 에서 state 를 다시 되돌려주며, 
 * 값을 비교한다. 
 * 
 * 왜냐하면, CSRF 공격을 방어하기 위해서
 * 메모리에 상주시키다가 비교한 이후에 remove 시킨다.
 * **/
public class StateVo {
	private String state;
	
	public StateVo(String state){
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
}
