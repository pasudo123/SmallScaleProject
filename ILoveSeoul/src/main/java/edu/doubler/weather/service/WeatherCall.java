package edu.doubler.weather.service;

import edu.doubler.weather.domain.WeatherContext;

public interface WeatherCall {
	
	// Weather Context 획득
	public WeatherContext getWeatherContext(String lat, String lng);
}
