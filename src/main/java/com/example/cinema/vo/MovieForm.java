package com.example.cinema.vo;

import java.util.Date;

/**
 * @author fjj
 * @date 2019/3/12 6:20 PM
 */
public class MovieForm {
    /**
     * 电影id
     */
    private Integer id;
    /**
     * 电影名称
     */
    private String name;
    /**
     * 海报url
     */
    private String posterUrl;
    /**
     * 导演
     */
    private String director;
    /**
     * 编剧
     */
    private String screenWriter;
    /**
     * 主演
     */
    private String starring;
    /**
     * 电影类型
     */
    private String type;
    /**
     * 制片国家/地区
     */
    private String country;
    /**
     * 语言
     */
    private String language;
    /**
     * 上映时间
     */
    private Date startDate;
    /**
     * 片长
     */
    private Integer length;
    /**
     * 描述
     * @return
     */
    private String description;
    /**
     * 电影状态，0：上架状态，1：下架状态
     */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getScreenWriter() {
        return screenWriter;
    }

    public void setScreenWriter(String screenWriter) {
        this.screenWriter = screenWriter;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
