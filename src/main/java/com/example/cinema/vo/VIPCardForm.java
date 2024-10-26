package com.example.cinema.vo;

import com.example.cinema.po.VIP_Strategy;

/**
 * Created by liying on 2019/4/14.
 */
public class VIPCardForm {

    /**
     * vip卡id
     */
    private int vipId;

    /**
     * 付款金额
     */
    private int amount;

    /**
     *VIP_Strategy
     */
    private int vipStrategyId;


    public int getVipId() {
        return vipId;
    }

    public void setVipId(int vipId) {
        this.vipId = vipId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getVipStrategyID(){return vipStrategyId;}

    public void setVipStrategyID(int vip_strategy_id){this.vipStrategyId=vip_strategy_id;}

}
