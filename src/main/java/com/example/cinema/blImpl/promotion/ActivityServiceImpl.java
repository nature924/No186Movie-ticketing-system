package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.ActivityService;
import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.po.Activity;
import com.example.cinema.po.Coupon;
import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liying on 2019/4/20.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    CouponService couponService;

    @Override
    @Transactional
    public ResponseVO publishActivity(ActivityForm activityForm) {
        try {
            ResponseVO vo = couponService.addCoupon(activityForm.getCouponForm());
            Coupon coupon = (Coupon) vo.getContent();
            Activity activity = new Activity();
            activity.setName(activityForm.getName());
            activity.setDescription(activityForm.getName());
            activity.setStartTime(activityForm.getStartTime());
            activity.setEndTime(activityForm.getEndTime());
            activity.setCoupon(coupon);
            activityMapper.insertActivity(activity);
            if(activityForm.getMovieList()!=null&&activityForm.getMovieList().size()!=0){
                activityMapper.insertActivityAndMovie(activity.getId(), activityForm.getMovieList());
            }
            return ResponseVO.buildSuccess(activityMapper.selectById(activity.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getActivities() {
        try {
            return ResponseVO.buildSuccess(activityMapper.selectActivities().stream().map(Activity::getVO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

}
