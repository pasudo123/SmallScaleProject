package com.doubler.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubler.main.service.ColdService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private ColdService coldService;
	private Map<String, List<Object>> map;
	
	@RequestMapping(value = "/")
	public String home(Model model) {
		return "correlation";
	}
	
	@RequestMapping(value = "/viewCorrelationSource-Treatment", produces="application/json")
	@ResponseBody
	public String viewCorrelationSourceAndTreatment(Model model) throws JsonGenerationException, JsonMappingException, IOException{
		map = coldService.correlationSumSourceAndTreatment();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String returnJSON = objectMapper.writeValueAsString(map);
		
		return returnJSON;
	}
	
	@RequestMapping(value = "/viewCorrelationSource-LowestTemperature", produces="application/json")
	@ResponseBody
	public String viewCorrelationSourceAndLowestTemporature(Model model) throws JsonGenerationException, JsonMappingException, IOException{
		map = coldService.correlationSumSourceAndLowestTemperature();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String returnJSON = objectMapper.writeValueAsString(map);
		
		return returnJSON;
	}
	
	@RequestMapping(value = "/viewCorrelationSource-DiurnalRange", produces="application/json")
	@ResponseBody
	public String viewCorrelationSourceAndDiurnalRange(Model model) throws JsonGenerationException, JsonMappingException, IOException{
		map = coldService.correlationSumSourceAndDiurnalRange();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String returnJSON = objectMapper.writeValueAsString(map);
		
		return returnJSON;
	}
	
	@RequestMapping(value = "viewCorrelationLowestTemperature-Treatment", produces="application/json")
	@ResponseBody
	public String viewCorrelationLowestTemperatureAndTreatment(Model model) throws JsonGenerationException, JsonMappingException, IOException{
		map = coldService.correlationLowestTemperatureAndTreatment();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String returnJSON = objectMapper.writeValueAsString(map);
		
		return returnJSON;
	}
	
	@RequestMapping(value = "viewCorrelationLowestTemperature-Moisture", produces="application/json")
	@ResponseBody
	public String viewCorrelationLowestTemperatureAndMoisture(Model model) throws JsonGenerationException, JsonMappingException, IOException{
		map = coldService.correlationLowestTemperatureAndMoisture();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String returnJSON = objectMapper.writeValueAsString(map);
		
		return returnJSON;
	}
	
	@RequestMapping(value = "viewCorrelationLowestTemperature-ColdDate", produces="application/json")
	@ResponseBody
	public String viewCorrelationLowestTemperatureAndColdDate(Model model) throws JsonGenerationException, JsonMappingException, IOException{
		map = coldService.correlationLowestTemperatureAndColdDate();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String returnJSON = objectMapper.writeValueAsString(map);
		
		return returnJSON;
	}
}
