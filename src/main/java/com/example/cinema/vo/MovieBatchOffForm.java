package com.example.cinema.vo;

import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 9:51 PM
 */
public class MovieBatchOffForm {
    /**
     * 要下架的电影id列表
     */
    private List<Integer> movieIdList;

    public List<Integer> getMovieIdList() {
        return movieIdList;
    }

    public void setMovieIdList(List<Integer> movieIdList) {
        this.movieIdList = movieIdList;
    }
}
