package com.example.cinema.vo;

import com.example.cinema.po.Activity;
import com.example.cinema.po.Coupon;

import java.util.List;

/**
 * Created by liying on 2019/4/21.
 */
public class TicketWithCouponVO {

    private List<TicketVO> ticketVOList;

    private double total;

    private List<Coupon> coupons;

    private List<Activity> activities;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<TicketVO> getTicketVOList() {
        return ticketVOList;
    }

    public void setTicketVOList(List<TicketVO> ticketVOList) {
        this.ticketVOList = ticketVOList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }
}
