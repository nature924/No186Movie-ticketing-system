package com.example.cinema.blImpl.sales;
import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.bl.sales.RefundService;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.management.MovieMapper;
import com.example.cinema.data.sales.RefundPolicyMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.data.user.HistoryMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    ScheduleMapper scheduleMapper;
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    TicketService ticketService;
    @Autowired
    RefundPolicy refundPolicy;
    @Autowired
    RefundPolicyMapper refundPolicyMapper;
    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    HistoryMapper historyMapper;
    @Autowired
    CouponService couponService;
    @Override
    public ResponseVO refundTicket(int ticketId){//System.out.println("45");
        try {
            double returnBalance=0;//返回退票金额
            int ok = 0;
            //判断是否可以退票
            Ticket ticket = ticketMapper.selectTicketById(ticketId);
            double rate=canD(ticket);
            if (rate!=0) {

                ok = 1;
                //将票的状态设为失效
                ticketMapper.updateTicketState(ticket.getId(), 2);
                //如果是会员，退钱到会员卡
                ScheduleItem scheduleItem = scheduleMapper.selectScheduleById(ticket.getScheduleId());
                double fare = scheduleItem.getFare();//fare是手续费比例
                if (vipCardMapper.selectCardByUserId(ticket.getUserId()) != null) {
                    VIPCard vipCard = vipCardMapper.selectCardByUserId(ticket.getUserId());
                    double balance = vipCard.getBalance();
                    double returnTotal = balance + fare*(1-rate) ;
                    returnBalance=fare*(1-rate);
                    vipCardMapper.updateCardBalance(vipCard.getId(), returnTotal);//退钱
                }
                //退票记录到消费记录中
                Ticket ticket2=ticketMapper.selectTicketById(ticketId);
                historyItem history=new historyItem();
                history.setDescription("退票；金额："+returnBalance);
                history.setUserId(ticket2.getUserId());
                history.setKind(3);
                history.setMoney(returnBalance);
                historyMapper.insertHistory(history);
            }
            String message = ok == 0 ? "退票失败" : "退票成功";
            //System.out.println("ok:" + ok + "message:" + message);
            if(ok==0)
                returnBalance=-1;
            return ResponseVO.buildSuccess(returnBalance);
            //return ResponseVO.buildSuccess(ticketService.getTicketByUser(ticket.getUserId()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAllPolicy(){
        try {
            List<RefundPolicy> policies = refundPolicyMapper.getAllPolicy();
            /*for (RefundPolicy it : policies) {
                System.out.println(it.getId() + "  " + it.getTimeBefore());
            }*/
            return ResponseVO.buildSuccess(policies);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateRefundPolicy(RefundPolicy refundPolicy){
        try {//System.out.println(refundPolicy.getId());
            //System.out.println(refundPolicy.getTimeBefore());
            Movie movie=movieMapper.selectMovieById(refundPolicy.getMovieId());
            refundPolicy.setMovieName(movie.getName());//前端传过来没有电影名字，只有id
            refundPolicyMapper.updateRefundPolicy(refundPolicy);//修改退票策略
            return ResponseVO.buildSuccess("成功修改退票策略");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO addRefundPolicy(RefundPolicy refundPolicy){
        try {//System.out.println(refundPolicy.getTimeBefore()+" "+refundPolicy.getStartTime()+" "+refundPolicy.getEndTime());
            Movie movie=movieMapper.selectMovieById(refundPolicy.getMovieId());
            refundPolicy.setMovieName(movie.getName());
            refundPolicyMapper.insertRefundPolicy(refundPolicy);//新增退票策略
            return ResponseVO.buildSuccess("成功添加退票策略");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO deleteRefundPolicy(int id){
        try{
            refundPolicyMapper.deleteRefundPolicy(id);//根据id删除退票策略
            return ResponseVO.buildSuccess(refundPolicyMapper.getAllPolicy());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    public double canD(Ticket ticket){
        ScheduleItem fk=scheduleMapper.selectScheduleById(ticket.getScheduleId());//获得该电影票的场次信息
        int movieId=fk.getMovieId();//System.out.println("movieId:"+movieId+"  scheduleId:"+fk.getId());
        //判断该电影在该时间段是否有退票策略
        int check=havePolicy(movieId);//System.out.println("check="+check);
        if(check==0){
            return 0;
        }
        else{
            RefundPolicy policy=refundPolicyMapper.selectPolicyById(check);
            int validTime=policy.getTimeBefore();
            boolean result=false;//最终结果，是否能退票
            boolean timeCan=false;//是否时间允许
            boolean paid=false;//该电影票是否已付钱
            Date current = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fk.getStartTime());//System.out.println(calendar.getTime());
            //电影开始前n小时，那一时刻的时间
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - validTime);//System.out.println(calendar.getTime());
            //System.out.println(current);
            //
            if(current.before(calendar.getTime())){//判断当前时间是否在退票时间限制前
                timeCan=true;
            }
            if(ticket.getState()==1){
                paid=true;
            }
            if(timeCan&&paid){
                result=true;
            }
            if(result==true){//返回该退票策略的手续费占比
                return policy.getRate();
            }
            return 0;
        }

    }
    public int havePolicy(int movie_id){
        Calendar calendar = Calendar.getInstance();
        List<RefundPolicy> list=refundPolicyMapper.getAllPolicy();
        for(RefundPolicy it:list){//拿出所有策略，看是否有该电影的且还未过期的退票策略，若有，返回该退票策略id
            if(it.getMovieId()==movie_id){//System.out.println("165!");
                /*System.out.println(it.getEndTime());
                System.out.println(calendar.getTime());
                System.out.println(calendar.getTime().before(it.getEndTime()));*/
                if(calendar.getTime().before(it.getEndTime())){
                    return it.getId();
                }
            }
        }
        return 0;
    }
}
