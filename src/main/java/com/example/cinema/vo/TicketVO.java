package com.example.cinema.vo;

import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/16.
 */
public class TicketVO {

    /**
     * 电影票id
     */
    private int id;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 排片id
     */
    private int scheduleId;
    /**
     * 列号
     */
    private int columnIndex;
    /**
     * 排号
     */
    private int rowIndex;

    /**
     * 订单状态
     */
    private String state;

    private Timestamp time;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public TicketVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
