package com.sample.jersey.app;

public class Main {
    private String temp;


    public String getTemp() {
        return temp;
    }

    public void setTemp(String my_temp) {
        this.temp = my_temp;
    }

    public String toString() {
        return "The temp: " + getTemp();
    }
}

