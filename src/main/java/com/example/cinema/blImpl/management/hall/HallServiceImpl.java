package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceImpl;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }
    public ResponseVO addHall(Hall addHallForm){//System.out.println("51!");
        try{
            Hall hall=new Hall();
            hall.setColumn(addHallForm.getColumn());
            hall.setName(addHallForm.getName());
            hall.setRow(addHallForm.getRow());
            hallMapper.insertHall(hall);//新增影厅
            List<Hall> halls=hallMapper.selectAllHall();
            return ResponseVO.buildSuccess(halls);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }
    public ResponseVO updateHall(Hall editHallForm){//System.out.println("58!");
        int id=editHallForm.getId();
        Date nowDate=new Date();
        ScheduleServiceImpl scheduleService=new ScheduleServiceImpl();
        //7天内该影厅有排片则不能修改该影厅
        Date nextDate=scheduleService.getNumDayAfterDate(nowDate,7);
        //7天内该影厅是否有排片
        List<ScheduleItem> scheduleItems= scheduleMapper.selectSchedule(id,nowDate,nextDate);
        if(scheduleItems.size()==0){
            try {
                hallMapper.updateHall(editHallForm);
                List<Hall> halls = hallMapper.selectAllHall();
                return ResponseVO.buildSuccess(halls);//返回修改后的所有影厅
            }catch(Exception e){
                e.printStackTrace();
                return ResponseVO.buildFailure("失败");
            }
        }
        else{
            return ResponseVO.buildFailure("七天内该影厅有排片");
        }
       // System.out.println("72");

    }
}
