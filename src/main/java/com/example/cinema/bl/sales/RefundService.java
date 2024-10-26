package com.example.cinema.bl.sales;

import com.example.cinema.po.RefundPolicy;
import com.example.cinema.po.Ticket;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;

import java.util.List;
public interface RefundService {
    //根据电影票id退票
    ResponseVO refundTicket(int ticketId);
    //获得所有退票策略
    ResponseVO getAllPolicy();
    //修改退票策略
    ResponseVO updateRefundPolicy(RefundPolicy refundPolicy);
    //新增退票策略
    ResponseVO addRefundPolicy(RefundPolicy refundPolicy);
    //根据id删除退票策略
    ResponseVO deleteRefundPolicy(int id);
}
