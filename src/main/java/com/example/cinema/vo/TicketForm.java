package com.example.cinema.vo;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
public class TicketForm {

    /**
     * 用户id
     */
    private int userId;
    /**
     * 排片id
     */
    private int scheduleId;

    private List<SeatForm> seats;

    public List<SeatForm> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatForm> seats) {
        this.seats = seats;
    }

    public TicketForm() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }



}
