package com.doubler.oauth.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubler.board.dao.BoardContentDTO;
import com.doubler.board.dao.BoardDAO;

@Service
public class ApiCallServiceImpl implements ApiCallService{
	
	@Autowired
	BoardDAO boardDao;
	Logger logger = LoggerFactory.getLogger(ApiCallServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	public String apiCallByRead(){
		List<BoardContentDTO> boardContentList = boardDao.getBoardList();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = null;
		
		logger.info("== " + "반환 데이터 JSON 형식으로 만들기");
		for(int i = 0; i < boardContentList.size(); i++){
			int number = boardContentList.get(i).getContentNum();
			
			jsonArray = new JSONArray();
			
			JSONObject jsonSubObject = new JSONObject();
			jsonSubObject.put("date", boardContentList.get(i).getContentDate());
			jsonSubObject.put("title", boardContentList.get(i).getContentTitle());
			jsonSubObject.put("writer", boardContentList.get(i).getContentWriter());
			jsonSubObject.put("content", boardContentList.get(i).getContentDetail());
			jsonSubObject.put("hit", boardContentList.get(i).getContentHit());
			jsonArray.add(jsonSubObject);
			jsonObject.put(number, jsonArray);
		}
		
		System.out.println(jsonObject.toJSONString());
		logger.info("== " + "반환 데이터 JSON 형식으로 만들기 완료");
		
		return jsonObject.toJSONString();
	}
	
	public String apiCallByWrite(String name, String title, String content){
		return null;
	}
	
	public String apiCallByDelete(int contentNum){
		return null;
	}
}
