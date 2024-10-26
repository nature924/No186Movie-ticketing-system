package com.example.cinema.po;

/**
 * @author fjj
 * @date 2019/4/21 2:51 PM
 */
public class AudiencePrice {
    private Integer userId;
    private Double totalPrice;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
