package com.example.cinema.vo;

public class PopularMovieVO {

    private String movieName;

    /**
     * 电影名
     */

    private Integer EffectiveDate;
    /**
     * 以“天”为单位，用于统计票房
     */

     private int boxOffice;

    /**
     * 在 EffectiveDate 天内对应电影的票房
     */

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(Integer effectiveDate) {
        this.EffectiveDate = effectiveDate;
    }

    public Integer getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(Integer boxOffice) {
        this.boxOffice = boxOffice;
    }

}
