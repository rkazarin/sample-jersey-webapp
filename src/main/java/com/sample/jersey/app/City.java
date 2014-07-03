package com.sample.jersey.app;

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
        sb.append("Name= " + getName() + "\n");
        sb.append("Temperature= " + getMain() + "\n");

        return sb.toString();
    }



}
