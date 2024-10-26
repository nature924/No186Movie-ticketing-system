package com.example.cinema.po;

/**
 * @author fjj
 * @date 2019/4/28 6:09 PM
 */
public class MovieScheduleTime {
    private Integer movieId;
    /**
     * 排片次数
     */
    private Integer time;
    private String name;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
