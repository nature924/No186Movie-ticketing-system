package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.ResponseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("{userId}/get")
    public ResponseVO getCoupons(@PathVariable int userId){
        return couponService.getCouponsByUser(userId);
    }

    @GetMapping("/get/all")
    public ResponseVO getAllCoupon(){return couponService.getAllCoupon();}

    @PostMapping("/send")
    public ResponseVO sendCoupon(@RequestParam int couponId,@RequestParam  int[] userId){return couponService.sendCoupon(couponId,userId);}

}
