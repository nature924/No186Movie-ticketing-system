package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.sales.RefundPolicyMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.user.HistoryMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.*;
import java.util.*;
import com.example.cinema.bl.user.AccountService;
import java.sql.Timestamp;
/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    AccountService accountService;
    @Autowired
    ScheduleMapper scheduleMapper;
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
    CouponService couponService;
    @Autowired
    HistoryMapper historyMapper;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        try{
            List<SeatForm> seats=ticketForm.getSeats();
            int numOfTickets=seats.size();
            for(int i=0;i<numOfTickets;i++){
                Ticket ticket=new Ticket();
                ticket.setUserId(ticketForm.getUserId());
                ticket.setScheduleId(ticketForm.getScheduleId());
                ticket.setColumnIndex(seats.get(i).getColumnIndex());
                ticket.setRowIndex(seats.get(i).getRowIndex());
                ticket.setState(0);
                ticketMapper.insertTicket(ticket);
            }
            TicketWithCouponVO ticketWithCouponVO=new TicketWithCouponVO();
            List<TicketVO> ticketsVOToShow=new ArrayList<>();
            int scheduleId=ticketForm.getScheduleId();
            ScheduleItem scheduleItem=scheduleService.getScheduleItemById(scheduleId);
            int movieId=scheduleItem.getMovieId();
            double total=0;
            List<Coupon> coupons=couponMapper.selectCouponByUser(ticketForm.getUserId());
            List<Activity> activities=activityMapper.selectActivitiesByMovie(movieId);
            for(int i=0;i<numOfTickets;i++){
                int rowIndex=seats.get(i).getRowIndex();
                int columnIndex=seats.get(i).getColumnIndex();
                Ticket ticketToShow=ticketMapper.selectTicketByScheduleIdAndSeat(scheduleId,columnIndex,rowIndex);
                TicketVO ticketVOToShow=ticketToShow.getVO();
                ticketsVOToShow.add(ticketVOToShow);
                total=total+scheduleItem.getFare();
            }
            ticketWithCouponVO.setTicketVOList(ticketsVOToShow);
            ticketWithCouponVO.setTotal(total);
            ticketWithCouponVO.setCoupons(coupons);
            ticketWithCouponVO.setActivities(activities);
            return ResponseVO.buildSuccess(ticketWithCouponVO);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public ResponseVO completeTicket(List<Integer> id, int couponId) {
        try{
            int userId=ticketMapper.selectTicketById(id.get(0)).getUserId();
            double total=0;
            for(int i=0;i<id.size();i++){
                total=total+scheduleService.getScheduleItemById(ticketMapper.selectTicketById(id.get(i)).getScheduleId()).getFare();
            }
            Coupon coupon=couponMapper.selectById(couponId);

            Timestamp t=new Timestamp(System.currentTimeMillis());
            boolean canUse=false;
            if(couponId!=0) {
                if (coupon.getStartTime().before(t) && coupon.getEndTime().after(t) && total >= coupon.getTargetAmount()) {
                    canUse = true;
                }
            }

            for(int i=0;i<id.size();i++){
                Ticket ticket=ticketMapper.selectTicketById(id.get(i));
                ticketMapper.updateTicketState(id.get(i),1);
            }
            Ticket ticket=ticketMapper.selectTicketById(id.get(0));
            int scheduleId=ticket.getScheduleId();
            int movieId=scheduleService.getScheduleItemById(scheduleId).getMovieId();
            List<Activity> activities=activityMapper.selectActivitiesByMovie(movieId);
            List<Coupon> coupons=new ArrayList<>();
            if(activities.size()!=0){
                for(int j=0;j<activities.size();j++){
                    Activity activity=activities.get(j);
                    Coupon couponOfActivity=activity.getCoupon();
                    couponMapper.insertCouponUser(couponOfActivity.getId(),userId);
                }
            }
            return ResponseVO.buildSuccess();


        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);
            Hall hall=hallService.getHallById(schedule.getHallId());
            int[][] seats=new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketByUser(userId);
            int numOfTickets=tickets.size();
            List<ShowTicketVO> toShow=new ArrayList<>();
            for(int i=0;i<numOfTickets;i++){
                ScheduleItem scheduleItem= scheduleService.getScheduleItemById(tickets.get(i).getScheduleId());
                ShowTicketVO showTicketVO=new ShowTicketVO();
                showTicketVO.setMovieName(scheduleItem.getMovieName());
                showTicketVO.setHallName(scheduleItem.getHallName());
                showTicketVO.setRow(tickets.get(i).getRowIndex());
                showTicketVO.setColumn(tickets.get(i).getColumnIndex());
                DateFormat formatter= new SimpleDateFormat("MM月dd日 HH:mm");
                String s1=formatter.format(scheduleItem.getStartTime());
                showTicketVO.setStartTime(s1);
                String s2=formatter.format(scheduleItem.getEndTime());
                showTicketVO.setEndTime(s2);
                if(tickets.get(i).getState()==0){
                    showTicketVO.setState("未支付");
                }
                else if(tickets.get(i).getState()==1){
                    showTicketVO.setState("已完成");
                }
                else{
                    showTicketVO.setState("已失效");
                }
                toShow.add(showTicketVO);
            }
            for(int i=0;i<numOfTickets;i++){//System.out.println(tickets.get(i).getId());
                toShow.get(i).setTicketId(tickets.get(i).getId());
            }
            for(int i=0;i<numOfTickets;i++){//System.out.println(toShow.get(i).getMovieName());

            }
            return ResponseVO.buildSuccess(toShow);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
        try{

            int userId=ticketMapper.selectTicketById(id.get(0)).getUserId();
            double total=0;
            int a=0;
            for(int i=0;i<id.size();i++){
                total=total+scheduleService.getScheduleItemById(ticketMapper.selectTicketById(id.get(i)).getScheduleId()).getFare();
            }
            boolean canUse=false;
            Coupon coupon=couponMapper.selectById(couponId);
            if(couponId!=0){
                Timestamp t=new Timestamp(System.currentTimeMillis());

                if(coupon.getStartTime().before(t)&&coupon.getEndTime().after(t)&&total>=coupon.getTargetAmount()){
                    canUse=true;
                }}
            if(canUse) {
                total = total - coupon.getDiscountAmount();
            }
            VIPCard vipCard=vipCardMapper.selectCardByUserId(ticketMapper.selectTicketById(id.get(0)).getUserId());
            double balance=vipCard.getBalance();
            if (balance < total) {
                return ResponseVO.buildFailure("会员卡余额不足");
            }
            else{
                vipCardMapper.updateCardBalance(vipCard.getId(),vipCard.getBalance()-total);
                //System.out.println(vipCard.getBalance());
                for(int i=0;i<id.size();i++){
                    Ticket ticket=ticketMapper.selectTicketById(id.get(i));
                    ticketMapper.updateTicketState(id.get(i),1);
                }
                Ticket ticket=ticketMapper.selectTicketById(id.get(0));
                int scheduleId=ticket.getScheduleId();
                int movieId=scheduleService.getScheduleItemById(scheduleId).getMovieId();
                List<Activity> activities=activityMapper.selectActivitiesByMovie(movieId);
                List<Coupon> coupons=new ArrayList<>();
                if(activities.size()!=0){
                    for(int j=0;j<activities.size();j++){
                        Activity activity=activities.get(j);
                        Coupon couponOfActivity=activity.getCoupon();
                        couponMapper.insertCouponUser(couponOfActivity.getId(),userId);
                    }
                }
                return ResponseVO.buildSuccess();
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
        try{
            int numOfTickets=id.size();
            for(int i=0;i<numOfTickets;i++){
                if(ticketMapper.selectTicketById(id.get(i)).getState()==0) {
                    ticketMapper.deleteTicket(id.get(i));
                }
            }
            return ResponseVO.buildSuccess();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }



}
