package com.example.cinema.po;

import com.example.cinema.vo.TicketVO;
import com.example.cinema.vo.TicketWithScheduleVO;

import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/16.
 */
public class Ticket {

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
     * 订单状态：
     * 0：未完成 1：已完成 2:已失效
     */
    private int state;

    private Timestamp time;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Ticket() {
    }


    public TicketVO getVO() {
        TicketVO vo = new TicketVO();
        vo.setRowIndex(this.getRowIndex());
        vo.setColumnIndex(this.getColumnIndex());
        vo.setScheduleId(this.getScheduleId());
        vo.setId(this.getId());
        vo.setUserId(this.getUserId());
        String stateString;
        switch (state) {
            case 0:
                stateString = "未完成";
                break;
            case 1:
                stateString = "已完成";
                break;
            case 2:
                stateString = "已失效";
                break;
            default:
                stateString = "未完成";
        }
        vo.setState(stateString);
        vo.setTime(time);
        return vo;

    }
    public TicketWithScheduleVO getWithScheduleVO() {
        TicketWithScheduleVO vo = new TicketWithScheduleVO();
        vo.setRowIndex(this.getRowIndex());
        vo.setColumnIndex(this.getColumnIndex());
        vo.setId(this.getId());
        vo.setUserId(this.getUserId());
        String stateString;
        switch (state) {
            case 0:
                stateString = "未完成";
                break;
            case 1:
                stateString = "已完成";
                break;
            case 2:
                stateString = "已失效";
                break;
            default:
                stateString = "未完成";
        }
        vo.setState(stateString);
        return vo;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
