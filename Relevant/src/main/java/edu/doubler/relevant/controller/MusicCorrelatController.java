package edu.doubler.relevant.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.doubler.log_process.domain.KeywordMentionMapper;
import edu.doubler.relevant.keyword.KeywordMentionCorrelat;

@Controller
public class MusicCorrelatController {
	
	@Autowired
	KeywordMentionCorrelat keywordMentionCorrelat;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showMainView(){
		return "correlat/main";
	}
	
	@RequestMapping(value="/keyword")
	public String showKeywordView(Model model){
		
		// 결과값 가지고 오기.
		String path = "C:\\Users\\Daumsoft\\Desktop\\MelonLog\\click_log_2014";
		HashMap<String, ArrayList<KeywordMentionMapper>> map = keywordMentionCorrelat.getKeywordMention(path);
		
		if(map.get("daily") != null){
			System.out.println("daily 삽입");
			model.addAttribute("daily", map.get("daily").get(0));
			model.addAttribute("dailySize", map.get("daily").size());
		}
		
		if(map.get("weekly") != null){
			System.out.println("weekly 삽입");
			model.addAttribute("weekly", map.get("weekly"));
			model.addAttribute("weeklySize", map.get("weekly").size());
		}
		
		if(map.get("monthly") != null){
			System.out.println("monthly 삽입");
			model.addAttribute("monthly", map.get("monthly"));
			model.addAttribute("monthlySize", map.get("monthly").size());
		}
		
		return "correlat/main";
	}
}
