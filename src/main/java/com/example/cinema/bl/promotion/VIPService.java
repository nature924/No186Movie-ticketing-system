package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;



/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(int userId);

    ResponseVO getCardById(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPCardForm vipCardForm);

    ResponseVO getCardByUserId(int userId);

    ResponseVO issueVIP_Strategy(int chargeLimit, int giftAmount);

    ResponseVO changeVIP_Strategy(int VIP_Strategy_ID, int chargeLimit, int giftAmount);

    public ResponseVO getAllVip_Strategy();

    ResponseVO deleteVIP_Strategy(int VIP_Strategy_ID);

    ResponseVO getVipByMoney(int money);
}
