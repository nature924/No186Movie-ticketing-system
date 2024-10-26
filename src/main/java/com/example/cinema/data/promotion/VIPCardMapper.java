package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIP_Strategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.*;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {

    int insertOneCard(VIPCard vipCard);

    VIPCard selectCardById(int id);

    void updateCardBalance(@Param("id") int id, @Param("balance") double balance);

    void updateCardTotal(@Param("id") int id, @Param("total") double total);

    VIPCard selectCardByUserId(int userId);

    VIP_Strategy selectVIP_StrategyById(int id);

    int addVIP_Strategy(VIP_Strategy vip_strategy);

    void changeVIP_Strategy(int VIP_Strategy_ID, int chargeLimit, int giftAmount);

    List<VIP_Strategy> getVIP_Strategy();

    void deleteVIP_Strategy(int VIP_Strategy_ID);

    List<VIPCard> selectAllVip();
}
