package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

@Component
public class ForecastDisplay implements Observer, DisplayElement {
    private float totalTemperature;
    private float avgTemperature;

    private float minTemperature;

    private float maxTemperature;

    private int measurementsCount;

    private Subject weatherData;

    public ForecastDisplay(Subject weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public String display() {
        String html = "";
        html += String.format("<div style=\"background-image: " +
                "url(/images/sky.webp); " +
                "height: 400px; " +
                "width: 647.2px;" +
                "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                "\">");
        html += "<section>";
        html += String.format("<label>Average Temperature: %s</label><br />", avgTemperature);
        html += String.format("<label>Minimum Temperature: %s</label><br />", minTemperature);
        html += String.format("<label>Maximum Temperature: %s</label>", maxTemperature);
        html += "</section>";
        html += "</div>";
        return html;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.measurementsCount++;
        this.totalTemperature += temperature;
        this.avgTemperature = this.totalTemperature / this.measurementsCount;
        if (this.measurementsCount == 1) {
            this.minTemperature = temperature;
            this.maxTemperature = temperature;
        } else {
            if (temperature < this.minTemperature) {
                this.minTemperature = temperature;
            }
            if (temperature > this.maxTemperature) {
                this.maxTemperature = temperature;
            }
        }
    }

    @Override
    public String name() {
        return "Forecast Display";
    }

    @Override
    public String id() {
        return "forecast-display";
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}
