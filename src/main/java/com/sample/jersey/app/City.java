package com.sample.jersey.app;

//POJO for parsing JSON data from weather API

public class City {

    private String name;
    private Main main;

    public String getName() {
        return name;
    }

    public Main getMain() {
        return main;
    }

    public void setName(String my_city) {
        this.name = my_city;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
        sb.append(getMain());

        return sb.toString();
    }



}
