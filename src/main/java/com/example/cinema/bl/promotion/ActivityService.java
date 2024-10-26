package com.example.cinema.bl.promotion;

import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/20.
 */
public interface ActivityService {
    
    ResponseVO publishActivity(ActivityForm activityForm);

    ResponseVO getActivities();




}
