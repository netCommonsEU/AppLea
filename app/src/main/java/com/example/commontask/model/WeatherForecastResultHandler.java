package com.example.commontask.model;

public interface WeatherForecastResultHandler {
    void processResources(CompleteWeatherForecast completeWeatherForecast, long lastUpdate);
    void processError(Exception e);
}
