package com.example.cinema.controller.sales;


import com.example.cinema.bl.sales.RefundService;
import com.example.cinema.po.RefundPolicy;
import com.example.cinema.vo.ResponseVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
public class RefundController {
    @Autowired
    RefundService refundService;

    @PostMapping("/refund")
    public ResponseVO refundTicket(@RequestParam int ticketId){//System.out.println("refundController  ticketId:"+ticketId);
        return refundService.refundTicket(ticketId);
    }

    @GetMapping("/get/all/refund")
    public ResponseVO getAllPolicy(){
        return refundService.getAllPolicy();
    }

    @PostMapping("/add/refund")
    public ResponseVO addRefundPolicy(@RequestBody RefundPolicy policy){
        return refundService.addRefundPolicy(policy);
    }

    @PostMapping("/update/refund")
    public ResponseVO updateRefundPolicy(@RequestBody RefundPolicy policy){
        return refundService.updateRefundPolicy(policy);
    }

    @PostMapping("/delete/refund")
    public ResponseVO deleteRefundPolicy(@RequestParam int target_id){
        return refundService.deleteRefundPolicy(target_id);
    }


}
