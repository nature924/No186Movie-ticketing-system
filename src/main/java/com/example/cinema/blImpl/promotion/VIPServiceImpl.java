package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.VIP_Strategy;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.po.User;
import com.example.cinema.po.VIPCard;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService {
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    AccountMapper accountMapper;
    @Override
    public ResponseVO addVIPCard(int userId) {
        User user=accountMapper.getAccountById(userId);
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        vipCard.setTotal(25);
        vipCard.setName(user.getUsername());
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        VIPInfoVO vipInfoVO = new VIPInfoVO();
        List<VIP_Strategy> strategies=vipCardMapper.getVIP_Strategy();
        List<String> description=new ArrayList<>();
        for(int i=0;i<strategies.size();i++){
            description.add(strategies.get(i).getDescription());
        }
        vipInfoVO.setDescription(description);
        vipInfoVO.setPrice(VIPCard.price);
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {
        //System.out.println(vipCardForm.getVipId()+" "+vipCardForm.getAmount()+" "+vipCardForm.getVipStrategyID());
        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        VIP_Strategy vip_strategy=vipCardMapper.selectVIP_StrategyById(vipCardForm.getVipStrategyID());
        double balance = vipCardForm.getAmount()/vip_strategy.getChargeLimit()*vip_strategy.getGiftAmount()+vipCardForm.getAmount();
        vipCard.setBalance(vipCard.getBalance() + balance);
        vipCard.setTotal(vipCard.getTotal()+vipCardForm.getAmount());
        try {
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
            vipCardMapper.updateCardTotal(vipCardForm.getVipId(), vipCard.getTotal());
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(vipCardForm.getVipId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            //System.out.println(vipCard.getBalance());
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issueVIP_Strategy(int chargeLimit,int giftAmount){
        try{
            VIP_Strategy vip_strategy=new VIP_Strategy();
            vip_strategy.setGiftAmount(giftAmount);
            vip_strategy.setChargeLimit(chargeLimit);
            int VIP_Strategy_ID=vipCardMapper.addVIP_Strategy(vip_strategy);//新增会员卡充值优惠策略
            return ResponseVO.buildSuccess(vipCardMapper.selectVIP_StrategyById(VIP_Strategy_ID));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO changeVIP_Strategy(int VIP_Strategy_ID,int chargeLimit,int giftAmount){
        try{
            vipCardMapper.changeVIP_Strategy(VIP_Strategy_ID,chargeLimit,giftAmount);//修改优惠策略
            return ResponseVO.buildSuccess(vipCardMapper.selectVIP_StrategyById(VIP_Strategy_ID));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAllVip_Strategy(){//System.out.println("119");
        try{//获取所有会员卡充值优惠策略
            List<VIP_Strategy> list=vipCardMapper.getVIP_Strategy();
            /*for(VIP_Strategy it:list){
                System.out.println(it.getId()+" "+it.getChargeLimit()+" "+it.getGiftAmount());
            }*/
            return (ResponseVO.buildSuccess(list));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteVIP_Strategy(int VIP_Strategy_ID){
        try{//根据id删除会员卡优惠策略
            vipCardMapper.deleteVIP_Strategy(VIP_Strategy_ID);
            return ResponseVO.buildSuccess();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO getVipByMoney(int money){
        try{//根据消费总额筛选vip名单
            List<VIPCard> list1=vipCardMapper.selectAllVip();//所有会员
            List<VIPCard> list2=new ArrayList<>();//返回的筛选名单
            for(VIPCard it:list1){
                if(it.getTotal()>=money){
                    list2.add(it);
                }
            }
            return ResponseVO.buildSuccess(list2);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
