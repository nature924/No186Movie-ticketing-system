package com.example.cinema.vo;

import java.util.Date;

/**
 * @author fjj
 * @date 2019/4/21 3:00 PM
 */
public class AudiencePriceVO {
    private Date date;
    /**
     * 客单价
     */
    private Double price;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
