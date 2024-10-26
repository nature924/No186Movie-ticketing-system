package com.example.cinema.vo;

import com.example.cinema.po.MovieTotalBoxOffice;

/**
 * @author fjj
 * @date 2019/4/21 1:42 PM
 */
public class MovieTotalBoxOfficeVO {
    private Integer movieId;
    /**
     * 票房(单位：元)，(PS:如果后续数据量大，可自行处理单位，如改成单位：万元)
     */
    private Integer boxOffice;
    private String name;

    public MovieTotalBoxOfficeVO(MovieTotalBoxOffice movieTotalBoxOffice){
        this.movieId = movieTotalBoxOffice.getMovieId();
        this.boxOffice = movieTotalBoxOffice.getBoxOffice();
        this.name = movieTotalBoxOffice.getName();
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(Integer boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
