package com.example.cinema.po;

import com.example.cinema.po.Coupon;
import com.example.cinema.vo.ActivityVO;
import com.example.cinema.vo.MovieVO;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liying on 2019/4/20.
 */
public class Activity {

    private int id;
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
    private List<Movie> movieList;
    /**
     * 优惠券规格
     */
    private Coupon coupon;


    public Activity() {

    }

    public ActivityVO getVO() {
        ActivityVO vo = new ActivityVO();
        vo.setId(id);
        vo.setCoupon(coupon);
        vo.setDescription(description);
        vo.setEndTime(endTime);
        vo.setStartTime(startTime);
        vo.setName(name);
        vo.setMovieList(movieList.stream().map(movie -> {
            MovieVO mvo = new MovieVO();
            mvo.setId(movie.getId());
            mvo.setName(movie.getName());
            mvo.setPosterUrl(movie.getPosterUrl());
            mvo.setDirector(movie.getDirector());
            mvo.setScreenWriter(movie.getScreenWriter());
            mvo.setStarring(movie.getStarring());
            mvo.setType(movie.getType());
            mvo.setCountry(movie.getCountry());
            mvo.setLanguage(movie.getLanguage());
            mvo.setStartDate(movie.getStartDate());
            mvo.setLength(movie.getLength());
            mvo.setDescription(movie.getDescription());
            mvo.setStatus(movie.getStatus());
            mvo.setIslike(movie.getIslike());
            mvo.setLikeCount(movie.getLikeCount());
            return mvo;
        } ).collect(Collectors.toList()));
        return vo;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}
