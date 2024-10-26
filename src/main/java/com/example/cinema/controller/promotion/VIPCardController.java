package com.example.cinema.controller.promotion;

import com.example.cinema.po.historyItem;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.bl.user.AccountService;
/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;
    @Autowired
    AccountService accountService;
    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId){
        return vipService.addVIPCard(userId);
    }
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo(){
        return vipService.getVIPInfo();
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm){
        return vipService.charge(vipCardForm);
    }

    @GetMapping("/get/all/strategy")
    public ResponseVO getAllStrategy(){
        return vipService.getAllVip_Strategy();
    }

    @PostMapping("/add/strategy")
    public ResponseVO addStrategy(@RequestParam int chargeLimit ,int giftAmount)
    {return vipService.issueVIP_Strategy(chargeLimit,giftAmount);}

    @PostMapping("/update/strategy")
    public ResponseVO updateStrategy(@RequestParam int id,int chargeLimit ,int giftAmount)
    {return vipService.changeVIP_Strategy(id,chargeLimit,giftAmount);}

    @PostMapping("delete/strategy")
    public  ResponseVO deleteStrategy(@RequestParam int id){return vipService.deleteVIP_Strategy(id);}

    @PostMapping("insert/history")
    public ResponseVO insertHistory(@RequestBody historyItem history){return accountService.insertHistory(history); }

    @GetMapping("get/by/money")
    public ResponseVO getByMoney(@RequestParam int target){return vipService.getVipByMoney(target);}
}
