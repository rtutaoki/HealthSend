package com.example.justloginregistertest;

public class Time {
    private String time;
    private String name;
    public Time(String time,String name)
    {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    @Override
    public String toString() {
        return "Time{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
