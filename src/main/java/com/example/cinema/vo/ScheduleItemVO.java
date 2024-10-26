package com.example.cinema.vo;

import com.example.cinema.po.ScheduleItem;

import java.util.Date;

/**
 * @author fjj
 * @date 2019/4/28 5:43 PM
 */
public class ScheduleItemVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 影厅id
     */
    private Integer hallId;
    /**
     * 影厅名称
     */
    private String hallName;
    /**
     * 电影id
     */
    private Integer movieId;
    /**
     * 电影名
     */
    private String movieName;
    /**
     * 开始放映时间
     */
    private Date startTime;
    /**
     * 结束放映时间
     */
    private Date endTime;
    /**
     * 票价
     */
    private double fare;

    public ScheduleItemVO(ScheduleItem scheduleItem){
        this.id = scheduleItem.getId();
        this.hallId = scheduleItem.getHallId();
        this.hallName = scheduleItem.getHallName();
        this.movieId = scheduleItem.getMovieId();
        this.movieName = scheduleItem.getMovieName();
        this.startTime = scheduleItem.getStartTime();
        this.endTime = scheduleItem.getEndTime();
        this.fare = scheduleItem.getFare();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }
}
