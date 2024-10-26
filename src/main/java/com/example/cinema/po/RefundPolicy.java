package com.example.cinema.po;

import com.example.cinema.data.management.ScheduleMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class RefundPolicy {
    private Integer id;
    private int timeBefore;
    private Timestamp startTime;
    private Timestamp endTime;
    private int movieId;
    private String movieName;
    private double rate;
    public double getRate(){return this.rate;}
    public void setRate(double i){this.rate=i;}
    public String getMovieName(){return this.movieName;}
    public void setMovieName(String i){this.movieName=i;}
    public int getMovieId(){
        return  this.movieId;
    }
    public void setMovieId(int i){
        this.movieId=i;
    }
    public int getId(){
        return  this.id;
    }
    public void setId(int i){
        this.id=i;
    }
    public int getTimeBefore(){
        return timeBefore;
    }
    public void setTimeBefore(int i){
        this.timeBefore=i;
    }
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
