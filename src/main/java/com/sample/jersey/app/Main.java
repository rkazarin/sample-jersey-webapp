package com.sample.jersey.app;

//POJO for parsing temperature from weather API. For use with City class

public class Main {
    private String temp;


    public String getTemp() {
        double farenheit = ((Double.parseDouble(temp) - 273.15) * 1.8) + 32;
        farenheit = (double)Math.round(farenheit * 10)/10;
        return String.valueOf(farenheit);
    }

    public void setTemp(String my_temp) {
        this.temp = my_temp;
    }

    public String toString() {
        return getTemp();
    }
}

