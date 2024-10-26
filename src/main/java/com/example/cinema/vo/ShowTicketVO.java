package com.example.cinema.vo;

import java.util.*;

public class ShowTicketVO {
    private int ticketId;
    private String movieName;
    private String hallName;
    private int row;
    private int column;
    private String startTime;
    private String endTime;
    private String state;
    public void ShowTicketVO(){
    }
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setTicketId(int id){this.ticketId=id;}

    public int getTicketId(){return  ticketId;}

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }



    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row= row;
    }



    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }



    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }



    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



}
