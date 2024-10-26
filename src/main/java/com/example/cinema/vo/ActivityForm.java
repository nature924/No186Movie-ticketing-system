package com.example.cinema.vo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
public class ActivityForm {
    /**
     * 优惠活动名称
     */
    private String name;
    /**
     * 优惠活动描述
     */
    private String description;
    /**
     * 优惠活动开始时间
     */
    private Timestamp startTime;
    /**
     * 优惠活动截止时间
     */
    private Timestamp endTime;
    /**
     * 优惠电影列表
     */
    private List<Integer> movieList;
    /**
     * 优惠券规格
     */
    private CouponForm couponForm;


    public ActivityForm() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Integer> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Integer> movieList) {
        this.movieList = movieList;
    }

    public CouponForm getCouponForm() {
        return couponForm;
    }

    public void setCouponForm(CouponForm couponForm) {
        this.couponForm = couponForm;
    }
}
