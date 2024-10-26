package com.example.cinema.po;

public class VIP_Strategy {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * id
     */
    int id;

    public int getChargeLimit() {
        return chargeLimit;
    }

    public void setChargeLimit(int chargeLimit) {
        this.chargeLimit = chargeLimit;
    }

    /**
     * 充值额度
     */
    int chargeLimit;

    public int getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(int giftAmount) {
        this.giftAmount = giftAmount;
    }

    /**
     * 赠送额度
     */
    int giftAmount;


    public String getDescription(){
        return "满"+this.getChargeLimit()+"赠"+this.getGiftAmount();
    }

    /**
     * 满 chargeLimit 赠 giftAmount
     */
}
